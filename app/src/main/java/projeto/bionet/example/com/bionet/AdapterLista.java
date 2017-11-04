package projeto.bionet.example.com.bionet;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 02/11/2017.
 */

public class AdapterLista extends BaseAdapter {

    private final List<Coleta> coletas;
    private final Activity act;
    int selectedIndex = -1;
    private RadioButton listRadioButton = null;

    public AdapterLista(List<Coleta> coletas, Activity act) {
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
        View view = act.getLayoutInflater().inflate(R.layout.layout_lista_coletas, parent, false);
        String desc = "";

        Coleta coleta = coletas.get(position);

        TextView tituloColeta = (TextView)
                view.findViewById(R.id.tituloColeta);
        TextView descricao = (TextView)
                view.findViewById(R.id.descricaoColeta);
        ImageView imagem = (ImageView)
                view.findViewById(R.id.imagemColeta);

        String titulo = coleta.getModalidade()+": "+coleta.getQuantidade()+" "+coleta.getMedida()+"(s) \n"+coleta.getMaterial();

        if (coleta.getModalidade().equalsIgnoreCase("Venda")){
            desc = "R$: "+coleta.getValor()+" Reais\nEntrega: "+coleta.getEntrega()+"\nLocalidade: "
                    +coleta.getCidade()+"/"+coleta.getEstado();
        }else{
            desc = "Entrega: "+coleta.getEntrega()+"\nLocalidade: "
                    +coleta.getCidade()+"/"+coleta.getEstado();
        }

        tituloColeta.setText(titulo);
        descricao.setText(desc);
        imagem.setImageResource(R.drawable.bionet);


        return view;
    }


}
