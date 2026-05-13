package pagamentos.futebol.repository;

/*
    Interface Repository para Jogador
*/
@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long>{

    List<Jogador> findByNome(String nome);
    List<Jogador> findByEmail(String email);
    List<Jogador> findByDataNasc(LocalDate dataNasc);
}