import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

public class Leilao {
    int id;
    int taxaMaxima;
    int montanteTotal;
    String empresa;
    Date data;
    boolean sucesso;
    List<Licitacao> licitacoes;

    public Leilao(){
        this.id = 0;
        this.taxaMaxima = 0;
        this.montanteTotal = 0;
        this.empresa = null;
        this.data = null;
        this.sucesso = false;
        this.licitacoes = null;
    }

}