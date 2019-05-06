package com.hqxh.fiamproperty.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.bean.R_APPROVE.Result;
import com.hqxh.fiamproperty.ui.adapter.ApprovalAdapter;

import java.util.List;

/**
 * Created by apple on 17/8/11.
 */

//自定义弹出框

public class ConfirmDialog extends Dialog {


    private static String TAG = "ConfirmDialog";

    public ConfirmDialog(Context context) {
        super(context);
    }

    public ConfirmDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context; //上下问
        private String title;  //标题
        private List<Result> results; //意见选项
        private String message;//意见
        private String positiveButtonText; //确定
        private String negativeButtonText; //取消
        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        private EditText memoEdit; //意见

        private int mpostion;
        private Button positiveButton;

        private cOnClickListener cOnClickListener;

        private Result result;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        //自定义确定事件
        public interface cOnClickListener {
            public void cOnClickListener(android.content.DialogInterface dialogInterface, Result result,String memo);
        }

        public Builder.cOnClickListener getcOnClickListener() {
            return cOnClickListener;
        }

        public void setcOnClickListener(Builder.cOnClickListener cOnClickListener) {
            this.cOnClickListener = cOnClickListener;
        }

        /**
         * Set the Dialog message from resource
         *
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }


        public Builder setData(List<Result> result) {
            this.results = result;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         cOnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.cOnClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public ConfirmDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final ConfirmDialog dialog = new ConfirmDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_normal_layout, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            // set the dialog title
            ((TextView) layout.findViewById(R.id.title)).setText(title);

            memoEdit = (EditText) layout.findViewById(R.id.memo_text_id);
            positiveButton = (Button) layout.findViewById(R.id.positiveButton);
            // set the confirm button
            if (positiveButtonText != null) {//确定
                ((Button) layout.findViewById(R.id.positiveButton))
                        .setText(positiveButtonText);
//                if (positiveButtonClickListener != null) {
//                    ((Button) layout.findViewById(R.id.positiveButton))
//                            .setOnClickListener(new View.OnClickListener() {
//                                public void onClick(View v) {
//                                    positiveButtonClickListener.onClick(dialog,
//                                            DialogInterface.BUTTON_POSITIVE);
//                                }
//                            });
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e(TAG, "mpostion="+mpostion);
                        if (null == result) {
                            result = results.get(0);
                        }
                        String memo=memoEdit.getText().toString();
                        if(memo.equals("")&&mpostion==1){
                            memoEdit.setError("请输入意见");
                        }else {
                            if(memo.equals("")&&mpostion==0){
                                memo="同意";
                            }
                            cOnClickListener.cOnClickListener(dialog, result, memo);
                        }
                    }
                });
//                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {//取消
                ((Button) layout.findViewById(R.id.negativeButton))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.negativeButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.negativeButton).setVisibility(
                        View.GONE);
            }
            // set the content message
            if (message != null) {
                ((EditText) layout.findViewById(R.id.memo_text_id)).setText(message);
            }

            if (results != null) {
                ListView listView = (ListView) layout.findViewById(R.id.listview_id);
                ApprovalAdapter approvalAdapter = new ApprovalAdapter(context, results);
                listView.setAdapter(approvalAdapter);
                approvalAdapter.setOnclicklistener(new ApprovalAdapter.cOnClickListener() {
                    @Override
                    public void cOnClickListener(int postion) {
                        result = results.get(postion);
                        mpostion=postion;
//                        memoEdit.setText(result.getInstruction());
                    }
                });
            }
            dialog.setContentView(layout);
            return dialog;
        }


    }


}
