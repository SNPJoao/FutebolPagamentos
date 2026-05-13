package pagamentos.futebol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/*
    Controlador dos pagamentos
*/
@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private JogadorRepository jogadorRepository;

    // Listar pagamentos por ano, mes, valor ou todos eles
    @GetMapping("/")
    public ResponseEntity<List<Pagamento>> listarTodos(@RequestParam(required = false) Short ano, @RequestParam(required = false) Byte mes,
                                                        @RequestParam(required = false) BigDecimal valor){

         try{
            List<Pagamento> pagamentos = new ArrayList<Pagamento>();
            if(ano == null && mes == null && valor == null)
                    pagamentoRepository.findAll().forEach(pagamentos::add);
            else{
                if(ano != null)
                    pagamentoRepository.findByAno(ano).forEach(pagamentos::add);
                if(mes != null)
                    pagamentoRepository.findByMes(mes).forEach(pagamentos::add);
                if(valor != null)
                    pagamentoRepository.findByValor(valor).forEach(pagamentos::add);
            }

            if(pagamentos.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            else return new ResponseEntity<>(pagamentos, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar pagamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> buscarPorId(@PathVariable long id){
         try{
            Optional<Pagamento> data = pagamentoRepository.findById(id);

            if(data.isPresent()){
                Pagamento pagamento = data.get();
                return new ResponseEntity<>(pagamento, HttpStatus.OK);
            }
            else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Listar pagamentos de um jogador pelo seu id
    @GetMapping("/jogador/{id}")
    public ResponseEntity<List<Pagamento>> listarPorJogador(@PathVariable long id){
        try{
            Optional<Jogador> data = jogadorRepository.findById(id);
            if(data.isPresent()){
                Jogador jogador = data.get();
                List<Pagamento> pagamentos = new ArrayList<Pagamento>();
                pagamentoRepository.findByJogador(jogador).forEach(pagamentos::add);
                
                if(pagamentos.isEmpty())
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                else return new ResponseEntity<>(pagamentos, HttpStatus.OK);
            }
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/TotalPagoJogador/{id}")
    public ResponseEntity<BigDecimal> valorTotalPagoJogador(@PathVariable long id){
        try{
            Optional<Jogador> data = jogadorRepository.findById(id);
            if(data.isPresent()){
                Jogador jogador = data.get();
                List<Pagamento> pagamentos = new ArrayList<Pagamento>();
                pagamentoRepository.findByJogador(jogador).forEach(pagamentos::add);
                
                if(pagamentos.isEmpty())
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                else{
                    BigDecimal total = BigDecimal.ZERO;
                    for(Pagamento p : pagamentos)
                        total = total.add(p.getValor());
                    return new ResponseEntity<>(total, HttpStatus.OK);
                }
            }
            else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Criar novo pagamento
    @PostMapping
    public ResponseEntity<Pagamento> criarPagamento(@Valid @RequestBody Pagamento pagamento, @RequestParam long codJogador){
        try{
            Optional<Jogador> data = jogadorRepository.findById(codJogador);
            if(data.isPresent()){
                Jogador jogador = data.get();
                Pagamento pagamento_novo = pagamentoRepository.save(new Pagamento(pagamento.getAno(), pagamento.getMes(), pagamento.getValor(), jogador));
                return new ResponseEntity<>(pagamento_novo, HttpStatus.OK);
            }
            else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Atualizar pagamento por id
    @PutMapping("/{id}")
    public ResponseEntity<Pagamento> atualizarPagamento(@PathVariable long id, @Valid @RequestBody Pagamento pagamento){
        Optional<Pagamento> data = pagamentoRepository.findById(id);
        if(data.isPresent()){
            Pagamento pagamento_novo = data.get();
            pagamento_novo.setAno(pagamento.getAno());
            pagamento_novo.setMes(pagamento.getMes());
            pagamento_novo.setValor(pagamento.getValor());
            pagamento_novo.setJogador(pagamento.getJogador());
            return new ResponseEntity<>(pagamentoRepository.save(pagamento_novo), HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Remover pagamento por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable long id){
        try{
            pagamentoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Remover todos os pagamentos
    @DeleteMapping("/")
    public ResponseEntity<Void> deletar(){
        try{
            pagamentoRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}