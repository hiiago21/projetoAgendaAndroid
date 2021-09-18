package silva.hiago.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import silva.hiago.agenda.R;
import silva.hiago.agenda.dao.AlunoDAO;
import silva.hiago.agenda.model.Aluno;

public class FormularioAlunoActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR_NOVO = "Novo aluno";
    public static final String TITULO_APPBAR_EDICAO = "Editar aluno";
    public static final String CHAVE_ALUNO = "alunoSelecionado";

    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoEmail;
    private final AlunoDAO dao = new AlunoDAO();
    private Aluno alunoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);

        inicializacaoDosCampos();
        configuraBotaoSalvar();

        carregaAluno();
    }

    private void carregaAluno() {
        Intent dados = getIntent();

        if(dados.hasExtra(CHAVE_ALUNO)){
            setTitle(TITULO_APPBAR_EDICAO);
            alunoSelecionado = (Aluno) dados.getSerializableExtra(CHAVE_ALUNO);
            preencheCampos();
        }else {
            setTitle(TITULO_APPBAR_NOVO);
            alunoSelecionado = new Aluno();
        }
    }

    private void preencheCampos() {
        campoNome.setText(alunoSelecionado.getNome());
        campoTelefone.setText(alunoSelecionado.getTelefone());
        campoEmail.setText(alunoSelecionado.getEmail());
    }

    private void configuraBotaoSalvar() {
        Button botaoSalvar = findViewById(R.id.activity_formulario_aluno_botao_salvar);
        botaoSalvar.setOnClickListener(view -> {
            preencheAluno();
            if(alunoSelecionado.temIdValido()){
                dao.editar(alunoSelecionado);
            }else{
                dao.salva(alunoSelecionado);
            }
            finish();
        });
    }

    private void inicializacaoDosCampos() {
        campoNome = findViewById(R.id.activity_formulario_aluno_nome);
        campoTelefone = findViewById(R.id.activity_formulario_aluno_telefone);
        campoEmail = findViewById(R.id.activity_formulario_aluno_email);
    }

    private void preencheAluno() {
        String nome = campoNome.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoEmail.getText().toString();

        alunoSelecionado.setNome(nome);
        alunoSelecionado.setTelefone(telefone);
        alunoSelecionado.setEmail(email);
    }
}
