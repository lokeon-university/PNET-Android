package es.manguca.Utils;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import es.manguca.R;

public class PDFRenderActivity extends AppCompatActivity {

    private PdfRenderer renderer;
    private PdfRenderer.Page currentPage;
    private ImageView imgPdf;
    private Button btnNextPage;
    private Button btnPrevPage;
    private ParcelFileDescriptor parcelFileDescriptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_layout);

        imgPdf = (ImageView) findViewById(R.id.imgPdf);
        btnNextPage = (Button) findViewById(R.id.btnNextPage);
        btnPrevPage = (Button) findViewById(R.id.btnPrevPage);

        View.OnClickListener clickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (renderer != null && currentPage != null) {
                    if (v == btnNextPage)
                        renderPage(currentPage.getIndex() + 1);
                    else if (v == btnPrevPage)
                        renderPage(currentPage.getIndex() - 1);
                }
            }
        };

        btnNextPage.setOnClickListener(clickListener);
        btnPrevPage.setOnClickListener(clickListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initRenderer();
        renderPage(0);
    }


    private void renderPage(int pageIndex) {

        if (currentPage != null) {
            currentPage.close();
        }

        currentPage = renderer.openPage(pageIndex);
        Bitmap bitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(), Bitmap.Config.ARGB_8888);
        currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        imgPdf.setImageBitmap(bitmap);
        btnPrevPage.setEnabled(currentPage.getIndex() > 0);
        btnNextPage.setEnabled(currentPage.getIndex() + 1 < renderer.getPageCount());

    }


    private void initRenderer() {
        try {

            File temp = new File(getCacheDir(), "tempPdf.pdf");
            FileOutputStream fos = new FileOutputStream(temp);
            InputStream is = getAssets().open("horarios.pdf");

            byte[] buffer = new byte[1024];
            int readBytes;
            while ((readBytes = is.read(buffer)) != -1)
                fos.write(buffer, 0, readBytes);

            fos.close();
            is.close();

            parcelFileDescriptor = ParcelFileDescriptor.open(temp, ParcelFileDescriptor.MODE_READ_ONLY);
            renderer = new PdfRenderer(parcelFileDescriptor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        if (isFinishing()) {
            if (currentPage != null)
                currentPage.close();
            try {
                parcelFileDescriptor.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            renderer.close();
        }
        super.onPause();
    }
}