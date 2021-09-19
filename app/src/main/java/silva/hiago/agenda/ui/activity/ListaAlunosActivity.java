package silva.hiago.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import silva.hiago.agenda.R;
import silva.hiago.agenda.dao.AlunoDAO;
import silva.hiago.agenda.model.Aluno;
import silva.hiago.agenda.ui.adapter.ListaAlunosAdapter;

public class ListaAlunosActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Lista de alunos";
    public static final String CHAVE_ALUNO = "alunoSelecionado";
    private final AlunoDAO dao = new AlunoDAO();
    private ListaAlunosAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        setTitle(TITULO_APPBAR);
        configuraFabNovoAluno();
        configuraLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_alunos_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.activity_formulario_alunos_menu_remover){

            confirmaRemocao(item);
        }

        return super.onContextItemSelected(item);
    }

    private void confirmaRemocao(@NonNull final MenuItem item) {
        new AlertDialog.Builder(this)
                .setTitle("Removenvo Aluno")
                .setMessage("Tem certeza que deseja remover ? ")
                .setPositiveButton("Sim", (dialogInterface, i) -> {
                    AdapterView.AdapterContextMenuInfo menuInfoForAdapter = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                    Aluno alunoEscolhido = adapter.getItem(menuInfoForAdapter.position);
                    removeAluno(alunoEscolhido);
                })
                .setNegativeButton("Não", null)
                .show();
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
        atualizaAlunos();

    }

    private void atualizaAlunos() {
        adapter.clear();
        adapter.addAll(dao.todos());
    }

    private void configuraLista() {
        ListView listaDeAlunos = findViewById(R.id.activity_lista_alunos_listview);
        configuraAdapter(listaDeAlunos);
        configuraListenerDeclickPorItem(listaDeAlunos);
        registerForContextMenu(listaDeAlunos);
    }


    private void removeAluno(Aluno alunoRemover) {
        dao.remove(alunoRemover);
        adapter.remove(alunoRemover);
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

    private void configuraAdapter(ListView listaDeAlunos) {
        adapter = new ListaAlunosAdapter(this);
        listaDeAlunos.setAdapter(adapter);
    }
}
