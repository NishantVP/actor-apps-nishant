package im.actor.messenger.app.fragment.chat.messages;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.IOException;

import im.actor.core.entity.Message;
import im.actor.messenger.R;
import im.actor.messenger.app.AnimatedGifDrawable;
import im.actor.messenger.app.AnimatedImageSpan;
import im.actor.messenger.app.util.TextUtils;
import im.actor.messenger.app.view.Fonts;
import im.actor.messenger.app.view.TintImageView;
import im.actor.runtime.android.AndroidContext;

import static im.actor.messenger.app.core.Core.myUid;

public class TextHolder extends MessageHolder {

    private ViewGroup mainContainer;
    private FrameLayout messageBubble;
    private TextView text;
    private TextView time;
    private TintImageView status;

    private int waitColor;
    private int sentColor;
    private int deliveredColor;
    private int readColor;
    private int errorColor;

    public TextHolder(MessagesAdapter fragment, final View itemView) {
        super(fragment, itemView, false);

        mainContainer = (ViewGroup) itemView.findViewById(R.id.mainContainer);
        messageBubble = (FrameLayout) itemView.findViewById(R.id.fl_bubble);
        text = (TextView) itemView.findViewById(R.id.tv_text);
        text.setTypeface(Fonts.regular());

        time = (TextView) itemView.findViewById(R.id.tv_time);
        time.setTypeface(Fonts.regular());
        status = (TintImageView) itemView.findViewById(R.id.stateIcon);

        waitColor = itemView.getResources().getColor(R.color.conv_state_pending);
        sentColor = itemView.getResources().getColor(R.color.conv_state_sent);
        deliveredColor = itemView.getResources().getColor(R.color.conv_state_delivered);
        readColor = itemView.getResources().getColor(R.color.conv_state_read);
        errorColor = itemView.getResources().getColor(R.color.conv_state_error);
    }

    @Override
    protected void bindData(final Message message, boolean isUpdated, PreprocessedData preprocessedData) {
        PreprocessedTextData textData = (PreprocessedTextData) preprocessedData;
        CharSequence text;
        if (textData.getSpannableString() != null) {
            text = textData.getSpannableString();
        } else {
            text = textData.getText();
        }
        bindRawText(text, message, false);
    }

    public void bindRawText(CharSequence rawText, Message message, boolean isItalic) {
        if (message.getSenderId() == myUid()) {
            messageBubble.setBackgroundResource(R.drawable.bubble_text_out);
        } else {
            messageBubble.setBackgroundResource(R.drawable.bubble_text_in);
        }

        if (isItalic) {
            text.setTypeface(Fonts.italic());
        } else {
            text.setTypeface(Fonts.regular());
        }

        if(rawText.toString().equals("dummy"))
        {
            SpannableStringBuilder sb = new SpannableStringBuilder();
            String dummyText = "dummy";
             sb.append(dummyText);
            try {
                sb.setSpan(new AnimatedImageSpan(new AnimatedGifDrawable(AndroidContext.getContext().getAssets().open("pikachu.gif"), new AnimatedGifDrawable.UpdateListener() {
                    @Override
                    public void update() {
                        text.postInvalidate();
                    }
                })), sb.length() - 5, sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } catch (IOException e) {
                e.printStackTrace();
            }
            text.setText(sb);

        }
        else
        {
            text.setText(rawText);
        }


        // Fixing url long tap
        text.setMovementMethod(new CustomLinkMovementMethod());

        // Fixing span offsets
//        if (rawText instanceof Spannable) {
//            Spannable s = (Spannable) rawText;
//            QuoteSpan[] qSpans = s.getSpans(0, s.length(), QuoteSpan.class);
//            text.setMinimumWidth(0);
//            if (qSpans.length > 0) {
//                text.measure(0, 0);
//                text.setMinimumWidth(text.getMeasuredWidth() + qSpans[0].getLeadingMargin(true));
//            }
//        }

        if (message.getSenderId() == myUid()) {
            status.setVisibility(View.VISIBLE);

            switch (message.getMessageState()) {
                case SENT:
                    status.setResource(R.drawable.msg_check_1);
                    status.setTint(sentColor);
                    break;
                case RECEIVED:
                    status.setResource(R.drawable.msg_check_2);
                    status.setTint(deliveredColor);
                    break;
                case READ:
                    status.setResource(R.drawable.msg_check_2);
                    status.setTint(readColor);
                    break;
                default:
                case PENDING:
                    status.setResource(R.drawable.msg_clock);
                    status.setTint(waitColor);
                    break;
                case ERROR:
                    status.setResource(R.drawable.msg_error);
                    status.setTint(errorColor);
                    break;
            }
        } else {
            status.setVisibility(View.GONE);
        }

        time.setText(TextUtils.formatTime(message.getDate()));
    }

    class CustomLinkMovementMethod extends LinkMovementMethod {

        @Override
        public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent event) {
            super.onTouchEvent(textView, spannable, event);
            mainContainer.onTouchEvent(event);
            return true;
        }
    }
}
