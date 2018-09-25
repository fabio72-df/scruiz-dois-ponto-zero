/* Exemplo utilizado:

https://github.com/firebase/quickstart-android/tree/master/auth/app/src/main/java/com/google/firebase/quickstart/auth/java

 */

package com.fabiocarvalho.scruiz.authenticator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.fabiocarvalho.scruiz.BaseActivity;
import com.fabiocarvalho.scruiz.R;

public class ChooserActivity
        extends BaseActivity
        implements AdapterView.OnItemClickListener {

    private static final Class[] CLASSES = new Class[]{
            GoogleActivity.class,
            EmailActivity.class,
            LoginAnonimoActivity.class
    };
    private static final String[] MENU_TITULOS = {
            "Google",
            "E-mail",
            "Anônimo"
    };
    private static final int[] DESCRIPTION_IDS = new int[]{
            R.string.desc_google_sign_in,
            R.string.desc_email_sign_in,
            R.string.desc_anonimo_sign_in
    };
    private static final int[] IMAGENS_MENU = new int[]{
            R.drawable.google_300x300,
            R.drawable.img_email,
            R.drawable.anonimo
    };

    /*
    ////////////////////////////////////////////////////////////////////////////////////////////////
    >> ON CREATE
    ////////////////////////////////////////////////////////////////////////////////////////////////
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);

        // BARRA SUPERIOR
        Toolbar myToolbar = findViewById(R.id.toolbar_Chooser);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // BARRA SUPERIOR *** fim ***

        // Set up ListView and Adapter
        AdapterAuthenticator adapter = new AdapterAuthenticator(this);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        // Set up ListView and Adapter *** fim ***

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Class clicked = CLASSES[position];
        startActivity(new Intent(this, clicked));
    }

    public static class AdapterAuthenticator extends BaseAdapter {

        //private final Class[] CLASSES;
        private final Activity activity;

        private AdapterAuthenticator(Activity activity) {
            //this.CLASSES = CLASSES;
            this.activity = activity;
        }

        @Override
        public int getCount() {
            return CLASSES.length;
        }

        @Override
        public Object getItem(int position) {
            return CLASSES[position];
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = activity.getLayoutInflater().inflate(R.layout.lista_auth, parent, false);
            Class classe = CLASSES[position];
            //
            TextView nome = (TextView)
                    view.findViewById(R.id.lista_auth_nome);
            nome.setText(MENU_TITULOS[position]);
            //
            TextView descricao = (TextView)
                    view.findViewById(R.id.lista_auth_descricao);
            descricao.setText(DESCRIPTION_IDS[position]);
            ImageView imagem = (ImageView) view.findViewById(R.id.lista_auth_imagem);
            imagem.setImageResource(IMAGENS_MENU[position]);
            return view;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
   }
///
// FIM CHOOSER ACTIVITY
///
}
