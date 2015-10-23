package com.uphie.one.ui.question;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.uphie.one.R;
import com.uphie.one.abs.AbsBaseFragment;
import com.uphie.one.common.Api;
import com.uphie.one.common.Constants;
import com.uphie.one.common.HttpData;
import com.uphie.one.common.HttpError;
import com.uphie.one.utils.JsonUtil;
import com.uphie.one.utils.TimeUtil;
import com.uphie.one.widgets.LikeView;

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
    @Bind(R.id.liv_saying)
    LikeView lvSaying;
    @Bind(R.id.question_content)
    LinearLayout questionContent;
    @Bind(R.id.text_answer_editor)
    TextView textAnswerEditor;

    @Override
    public int getLayoutId() {
        return R.layout.layout_question;
    }

    @Override
    public void init() {

        lvSaying.addOnLikeChangeListener(this);

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
                questionContent.setVisibility(View.VISIBLE);

                textQuestionDate.setText(TimeUtil.getEngDate(question.strQuestionMarketTime));
                textQuestionTitle.setText(question.strQuestionTitle);
                textQuestionContent.setText(Html.fromHtml(question.strQuestionContent));
                textAnswerTitle.setText(question.strAnswerTitle);
                textAnswerContent.setText(Html.fromHtml(question.strAnswerContent));
                textAnswerEditor.setText(question.sEditor);
                lvSaying.setText(question.strPraiseNumber + "");
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
    public void onLiked(boolean like) {
        RequestParams requestParams = new RequestParams();
    }

}
