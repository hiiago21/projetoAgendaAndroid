package silva.hiago.agenda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import silva.hiago.agenda.R;
import silva.hiago.agenda.model.Aluno;

public class ListaAlunosAdapter extends BaseAdapter {

    private final List<Aluno> alunos = new ArrayList<>();
    private Context context;


    public ListaAlunosAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Aluno getItem(int posicao) {
        return alunos.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return alunos.get(posicao).getId();
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {

        View viewCriada = criaView(viewGroup);

        populaDadosNosItensDosTextViewAdapter(posicao, viewCriada);

        return viewCriada;
    }

    private void populaDadosNosItensDosTextViewAdapter(int posicao, View viewCriada) {
        TextView nome = viewCriada.findViewById(R.id.item_aluno_nome);
        TextView telefone = viewCriada.findViewById(R.id.item_aluno_telefone);

        nome.setText(alunos.get(posicao).getNome());
        telefone.setText(alunos.get(posicao).getTelefone());
    }

    private View criaView(ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_aluno, viewGroup, false);
    }

    public void clear() {
        alunos.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Aluno> todos) {
        alunos.addAll(todos);
        notifyDataSetChanged();
    }

    public void remove(Aluno alunoRemover) {
        alunos.remove(alunoRemover);
        notifyDataSetChanged();
    }
}
