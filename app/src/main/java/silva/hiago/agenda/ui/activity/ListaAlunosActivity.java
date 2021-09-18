package silva.hiago.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import silva.hiago.agenda.R;
import silva.hiago.agenda.dao.AlunoDAO;
import silva.hiago.agenda.model.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Lista de alunos";
    public static final String CHAVE_ALUNO = "alunoSelecionado";
    private final AlunoDAO dao = new AlunoDAO();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        setTitle(TITULO_APPBAR);
        configuraFabNovoAluno();
    }

    private void configuraFabNovoAluno() {
        FloatingActionButton botaoNovoAluno = findViewById(R.id.activity_lista_alunos_fab_novo_aluno);
        botaoNovoAluno.setOnClickListener(view -> abreFormularioInclusaoAlunoActivity());
    }

    private void abreFormularioInclusaoAlunoActivity() {
        startActivity(new Intent(this, FormularioAlunoActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        configuraLista();
    }

    private void configuraLista() {
        ListView listaDeAlunos = findViewById(R.id.activity_lista_alunos_listview);
        List<Aluno> todos = dao.todos();
        configuraAdapter(listaDeAlunos, todos);
        configuraListenerDeclickPorItem(listaDeAlunos);
    }

    private void configuraListenerDeclickPorItem(ListView listaDeAlunos) {
        listaDeAlunos.setOnItemClickListener((adapterView, view, posicaoLista, idItem) -> {
            Aluno alunoEscolhido = (Aluno) adapterView.getItemAtPosition(posicaoLista);

            abreActivityComInfoAlunoEdicao(alunoEscolhido);
        });
    }

    private void abreActivityComInfoAlunoEdicao(Aluno alunoEscolhido) {
        Intent formEdicao = new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class);
        formEdicao.putExtra(CHAVE_ALUNO, alunoEscolhido);
        startActivity(formEdicao);
    }

    private void configuraAdapter(ListView listaDeAlunos, List<Aluno> todos) {
        listaDeAlunos.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                todos));
    }
}
