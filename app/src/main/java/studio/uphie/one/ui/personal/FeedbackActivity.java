package studio.uphie.one.ui.personal;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.SyncListener;
import com.umeng.fb.model.Conversation;
import com.umeng.fb.model.Reply;
import com.umeng.fb.model.UserInfo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.OnClick;
import studio.uphie.one.R;
import studio.uphie.one.abs.AbsBaseActivity;
import studio.uphie.one.utils.SysUtil;
import studio.uphie.one.utils.TimeUtil;
import studio.uphie.one.widgets.ClearEditText;

/**
 * Created by Uphie on 2015/11/5.
 * Email: uphie7@gmail.com
 */
public class FeedbackActivity extends AbsBaseActivity {

    @Bind(R.id.listView)
    PullToRefreshListView listView;
    @Bind(R.id.edt_feedback)
    ClearEditText edt_feedback;
    @Bind(R.id.text_send)
    TextView text_send;

    private Conversation conversation;
    private MessageAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void init() {

        setTitle("意见反馈");

        conversation = new FeedbackAgent(this).getDefaultConversation();

        edt_feedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    text_send.setEnabled(true);
                    text_send.setTextColor(getResources().getColor(R.color.green));
                } else {
                    text_send.setEnabled(false);
                    text_send.setTextColor(getResources().getColor(R.color.disable_gray));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_feedback.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    onClick();
                    return true;
                }
                return false;
            }
        });
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                sync();
            }
        });

        adapter = new MessageAdapter(conversation);
        listView.setAdapter(adapter);
    }

    @OnClick(R.id.text_send)
    public void onClick() {
        closeInputMethod();
        String msg = edt_feedback.getText().toString().trim();
        edt_feedback.setText("");
        conversation.addUserReply(msg);
        adapter.notifyDataSetChanged();
        sync();
    }

    /**
     * 数据同步
     */
    private void sync() {
        conversation.sync(new SyncListener() {
            @Override

            public void onSendUserReply(List<Reply> replyList) {
            }

            @Override
            public void onReceiveDevReply(List<Reply> replyList) {
                // SwipeRefreshLayout停止刷新
                listView.onRefreshComplete();
                // 刷新ListView
                adapter.notifyDataSetChanged();
                //滚动到底部
                listView.getRefreshableView().setSelection(adapter.getCount() - 1);
            }
        });
    }

    private class MessageAdapter extends BaseAdapter {

        private static final int TYPE_USER = 0;
        private static final int TYPE_ONE = 1;

        private Conversation conversation;

        public MessageAdapter(Conversation conversation) {
            this.conversation = conversation;
        }

        @Override
        public int getCount() {
            return conversation.getReplyList().size();
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            return getItem(position).type.equals(Reply.TYPE_DEV_REPLY) ? TYPE_ONE : TYPE_USER;
        }

        @Override
        public Reply getItem(int position) {
            return conversation.getReplyList().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Reply reply = getItem(position);
            ViewHolder holder;
            int type = getItemViewType(position);

            if (convertView == null) {
                if (type == TYPE_ONE) {
                    convertView = View.inflate(FeedbackActivity.this, R.layout.list_item_conversion_left, null);
                } else {
                    convertView = View.inflate(FeedbackActivity.this, R.layout.list_item_conversion_right, null);
                }
                holder = new ViewHolder();
                holder.iv_avatar = (ImageView) convertView.findViewById(R.id.list_item_conversion_avatar);
                holder.iv_msg_fail = (ImageView) convertView.findViewById(R.id.list_item_conversion_fail);
                holder.text_msg = (TextView) convertView.findViewById(R.id.list_item_conversion_message);
                holder.text_time = (TextView) convertView.findViewById(R.id.list_item_conversion_time);
                holder.pg_msg = (ProgressBar) convertView.findViewById(R.id.list_item_conversion_prog);

                holder.iv_msg_fail.setOnClickListener(new OnResendListener(reply));

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //头像
            if (type == TYPE_ONE) {
                holder.iv_avatar.setImageResource(R.drawable.av_author);
            }
            //消息
            holder.text_msg.setText(reply.content);
            //消息状态
            switch (reply.status) {
                case Reply.STATUS_NOT_SENT:
                    //发送失败
                    holder.pg_msg.setVisibility(View.GONE);
                    holder.iv_msg_fail.setVisibility(View.VISIBLE);
                    break;
                case Reply.STATUS_SENDING:
                    //发送中
                    holder.pg_msg.setVisibility(View.VISIBLE);
                    holder.iv_msg_fail.setVisibility(View.GONE);
                    break;
                default:
                    //发送成功
                    holder.pg_msg.setVisibility(View.GONE);
                    holder.iv_msg_fail.setVisibility(View.GONE);
                    break;
            }

            if (position != 0) {
                //不是第一条
                Reply lastReply = getItem(position - 1);
                Date date = new Date(reply.created_at);
                if (reply.created_at - lastReply.created_at > 5 * 60 * 1000 && reply.created_at - lastReply.created_at < 24 * 60 * 60 * 1000) {
                    //如果两条消息时间相差大于5分钟,小于一天，显示15：40形式时间
                    holder.text_time.setVisibility(View.VISIBLE);
                    holder.text_time.setText(TimeUtil.toSimpleTime(date));
                } else if (reply.created_at - lastReply.created_at > 24 * 60 * 60 * 1000) {
                    //如果两条消息时间相差大于于一天，显示2015年1月27日 10:00:00形式时间
                    holder.text_time.setVisibility(View.VISIBLE);
                    holder.text_time.setText(TimeUtil.toCommonTime(date));
                } else {
                    holder.text_time.setVisibility(View.GONE);
                }
            }
            return convertView;
        }

        private class OnResendListener implements View.OnClickListener {
            private Reply reply;

            public OnResendListener(Reply reply) {
                this.reply = reply;
            }

            @Override
            public void onClick(View v) {
                if (reply.type.equals(Reply.TYPE_USER_REPLY)) {
                    //用户回复的，并且发送失败的状态
                    sync();
                }
            }
        }

        private class ViewHolder {
            ImageView iv_avatar;
            TextView text_msg;
            TextView text_time;
            ProgressBar pg_msg;
            ImageView iv_msg_fail;
        }
    }
}
