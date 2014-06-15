package com.eat.chapter4;

import android.text.Editable;


        import android.app.Activity;
        import android.os.Bundle;
        import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
        import com.eat.R;

        import java.io.IOException;
        import java.io.PipedReader;
        import java.io.PipedWriter;


public class PipeExampleActivity extends Activity {

    private static final String TAG = "PipeExampleActivity";
    private EditText editText;

    PipedReader r;
    PipedWriter w;

    private Thread workerThread;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        r = new PipedReader();
        w = new PipedWriter();

        try {
            w.connect(r);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_pipe);
        editText = (EditText) findViewById(R.id.edit_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                try {
                    // Only handle addition of characters
                    if(count > before) {
                        // Write the last entered character to the pipe
                        w.write(charSequence.subSequence(before, count).toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        workerThread = new Thread(new TextHandlerTask(r));
        workerThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        workerThread.interrupt();
        try {
            r.close();
            w.close();
        } catch (IOException e) {
        }
    }

    private static class TextHandlerTask implements Runnable {
        private final PipedReader reader;

        public TextHandlerTask(PipedReader reader){
            this.reader = reader;
        }
        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                try {
                    int i;
                    while((i = reader.read()) != -1){
                        char c = (char) i;
                        //ADD TEXT PROCESSING LOGIC HERE
                        Log.d(TAG, "char = " + c);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
