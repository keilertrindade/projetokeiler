package projeto.bionet.example.com.bionet;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by Administrator on 17/11/2017.
 */

public class AdapterListaResultado extends BaseAdapter {

    private final List<Coleta> coletas;
    private final Activity act;
    private StorageReference storageRef, imageRef;
    private FirebaseStorage storage;


    public AdapterListaResultado(List<Coleta> coletas, Activity act) {
        this.coletas = coletas;
        this.act = act;
    }


    @Override
    public int getCount() {
        return coletas.size();
    }

    @Override
    public Object getItem(int position) {
        return coletas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = act.getLayoutInflater().inflate(R.layout.layout_lista_resultado, parent, false);
        Coleta coleta = coletas.get(position);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        imageRef = storageRef.child("coleta/"+coleta.getId());
        String desc = "";

        TextView tituloColeta = (TextView)
                view.findViewById(R.id.tituloColeta);
        TextView descricao = (TextView)
                view.findViewById(R.id.descricaoColeta);
        ImageView imagem = (ImageView)
                view.findViewById(R.id.imagemColeta);

        String titulo = coleta.getModalidade()+": "+coleta.getQuantidade()+" "+coleta.getMedida()+"(s) \n"+coleta.getMaterial();

        if (coleta.getModalidade().equalsIgnoreCase("Venda")){
            desc = "R$: "+coleta.getValor()+" Reais\nEntrega: "+coleta.getEntrega()+"\nLocalidade: "
                    +coleta.getCidade()+"/"+coleta.getEstado()+"\nTel: "+coleta.getTelefone();
        }else{
            desc = "Entrega: "+coleta.getEntrega()+"\nLocalidade: "
                    +coleta.getCidade()+"/"+coleta.getEstado()+"\nTel: "+coleta.getTelefone();
        }

        Glide.with(view.getContext())
                .using(new FirebaseImageLoader())
                .load(imageRef)
                .into(imagem);

        tituloColeta.setText(titulo);
        descricao.setText(desc);

        return view;
    }


}
