package studio.uphie.one.ui.question;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import studio.uphie.one.R;
import studio.uphie.one.abs.AbsBaseFragment;
import studio.uphie.one.common.Api;
import studio.uphie.one.common.Constants;
import studio.uphie.one.common.HttpData;
import studio.uphie.one.common.HttpError;
import studio.uphie.one.ui.article.Article;
import studio.uphie.one.utils.JsonUtil;
import studio.uphie.one.utils.TimeUtil;
import studio.uphie.one.widgets.LikeView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Uphie on 2015/9/6.
 * Email: uphie7@gmail.com
 */
public class QuestionContentFragment extends AbsBaseFragment implements LikeView.OnLikeChangedListener {

    @Bind(R.id.text_question_date)
    TextView textQuestionDate;
    @Bind(R.id.text_question_title)
    TextView textQuestionTitle;
    @Bind(R.id.text_question_content)
    TextView textQuestionContent;
    @Bind(R.id.text_answer_title)
    TextView textAnswerTitle;
    @Bind(R.id.text_answer_content)
    TextView textAnswerContent;
    @Bind(R.id.liv_question)
    LikeView lvQuestion;
    @Bind(R.id.question_content)
    LinearLayout questionContent;
    @Bind(R.id.text_answer_editor)
    TextView textAnswerEditor;

    /**
     * 当前的问题
     */
    private Question curQuestion;

    @Override
    public int getLayoutId() {
        return R.layout.layout_question;
    }

    @Override
    public void init() {

        lvQuestion.addOnLikeChangeListener(this);

        Bundle bundle = getArguments();
        String date = bundle.getString(Constants.KEY_DATE);
        int index = bundle.getInt(Constants.KEY_INDEX);

        RequestParams params = new RequestParams();
        params.put("strDate", date);
        params.put("strRow", index);
        getHttpData(Api.URL_QUESTION, params,new HttpData("result", "questionAdEntity"));
    }

    @Override
    public void onDataOk(String url, String data) {
        switch (url) {
            case Api.URL_QUESTION:
                Question question = JsonUtil.getEntity(data, Question.class);
                if (question == null) {
                    return;
                }
                curQuestion=question;

                questionContent.setVisibility(View.VISIBLE);

                textQuestionDate.setText(TimeUtil.getEngDate(question.strQuestionMarketTime));
                textQuestionTitle.setText(question.strQuestionTitle);
                textQuestionContent.setText(Html.fromHtml(question.strQuestionContent));
                textAnswerTitle.setText(question.strAnswerTitle);
                textAnswerContent.setText(Html.fromHtml(question.strAnswerContent));
                textAnswerEditor.setText(question.sEditor);
                lvQuestion.setText(question.strPraiseNumber + "");
                break;
            case Api.URL_LIKE_OR_CANCLELIKE:
                try {
                    JSONObject jsonObject=new JSONObject(data);
                    int likeCount=jsonObject.optInt("strPraisednumber");
                    //若实际的喜欢数量与LikeView自增的结果值不同，显示实际的数量
                    if (likeCount!=lvQuestion.getLikeCount()){
                        lvQuestion.setText(likeCount+"");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onDataError(String url, HttpError error) {
        switch (url) {
            case Api.URL_QUESTION:
                //没有数据，删除并销毁自己
                QuestionFragment.adapter.removeLast();
                onDestroy();
                break;
        }

    }

    @Override
    public void onLikeChanged() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("strPraiseItemId", curQuestion.strQuestionId);
        //我不清楚strDeviceId为什么标识符，格式为“ffffffff-9248-aa0c-ffff-ffffaea9cc69”
        requestParams.put("strDeviceId", "");
        requestParams.put("strAppName", "ONE");
        requestParams.put("strPraiseItem", "QUESTION");
        getHttpData(Api.URL_LIKE_OR_CANCLELIKE,requestParams,new HttpData("result","entPraise"));
    }

    public Question getContentData() {
        return curQuestion;
    }
}
