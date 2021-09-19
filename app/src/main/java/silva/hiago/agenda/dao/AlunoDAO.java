package silva.hiago.agenda.dao;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import silva.hiago.agenda.model.Aluno;

public class AlunoDAO {

    private final static List<Aluno> alunos = new ArrayList<>();
    private static int contadorIds = 1;

    public void salva(Aluno aluno) {
        aluno.setId(contadorIds);
        alunos.add(aluno);
        atualizaIds();
    }

    private void atualizaIds() {
        contadorIds++;
    }

    public void editar(Aluno aluno){
        Optional<Aluno> alunoEditavel = buscaAluno(aluno);
        if(alunoEditavel.isPresent()){
            int posNaLista = alunos.indexOf(alunoEditavel.get());
            alunos.set(posNaLista, aluno);
        }
    }

    @NonNull
    private Optional<Aluno> buscaAluno(Aluno aluno) {
        Optional<Aluno> alunoEditavel = alunos.stream().filter(aluno1 -> aluno1.getId() == aluno.getId()).findFirst();
        return alunoEditavel;
    }

    public List<Aluno> todos() {
        return new ArrayList<>(alunos);
    }

    @SuppressLint("NewApi")
    public void remove(Aluno aluno) {
        Optional<Aluno> alunoRemovido = buscaAluno(aluno);
        if(alunoRemovido.isPresent())
        alunos.remove(alunoRemovido.get());
    }
}
