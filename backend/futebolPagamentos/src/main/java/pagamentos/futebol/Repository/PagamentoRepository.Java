package pagamentos.futebol.repository;

/*
    Interface Repository para Pagamento
*/
@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long>{
    
    List<Pagamento> findByAno(Short ano);
    List<Pagamento> findByMes(Byte mes);
    List<Pagamento> findByValor(BigDecimal valor);
    List<Pagamento> findByJogador(Jogador jogador);
}