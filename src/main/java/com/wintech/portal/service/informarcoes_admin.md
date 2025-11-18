Documentação — Funcionalidades de Administração (Thainara)
1. Método salvarNovoAluno — AlunoService

Implementei o método responsável por salvar um novo aluno no sistema com senha criptografada.

Lógica implementada:

1.Recupera a senha enviada no objeto Aluno

2.Criptografa a senha usando PasswordEncoder

3.Substitui a senha original pela senha criptografada

4.Salva o aluno no banco usando alunoRepository.save(novoAluno)

2. Método salvarNovoProfessor — ProfessorService

Mesma lógica do aluno:

1.Obtem a senha enviada no objeto Professor

2.Criptografa com PasswordEncoder

3.Atualiza o objeto

4.Salva no professorRepository

3. Método salvar — TurmaService

Implementei o método simples de criação de Turmas

4. Método salvar — DisciplinaService

Criação simples de disciplinas



