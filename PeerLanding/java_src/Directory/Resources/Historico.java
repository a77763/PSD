package Directory.Resources;

import Directory.Representations.*;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/*
        Licitacao lic1 = new Licitacao(1,"manel",30,200);
        Licitacao lic2 = new Licitacao(2,"joao",20,1000);
        List<Licitacao> lics = new ArrayList<>();
        lics.add(lic1);
        lics.add(lic2);
        Leilao lei = new Leilao(1,100,403,"jock",null,true,lics);
        List<Leilao> leiloes = new ArrayList<>();
        leiloes.add(lei);
        Empresa empe = new Empresa("jock",leiloes,null);

*/


@Path("/")
public class Historico {
    private HashMap<String, Empresa> empresas;

    public Historico() {
        this.empresas = null;
    }

    public Historico(HashMap<String, Empresa> empresas) {
        this.empresas = empresas;
    }


    @GET
    @Path("empresas")
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    public EmpresasRep getEmps() {
        List<String> emp = new ArrayList<>();

        if(this.empresas == null)
            throw new WebApplicationException(404);

        emp.addAll(this.empresas.keySet());

        EmpresasRep nomes = new EmpresasRep(emp);

        return nomes;
    }

    //TODO Post não estão a funcionar
    @POST
    @Path("empresa/{nome}")
    public Response put(@PathParam("nome") String nome){
        empresas.put(nome, null);
        return Response.status(201).build();
    }

    @GET
    @Path("empresa/{nome}")
    public EmpresaRep getEmp(@PathParam("nome") String nomeEmp) {
        String nome = null;

        for(String key : empresas.keySet())
            if(key.equals(nomeEmp)) {
                nome = nomeEmp;
                break;
            }

        if(nome == null){
            throw new WebApplicationException(404);
        }

        EmpresaRep empresa = new EmpresaRep(empresas.get(nome));

        return empresa;
    }

    @GET
    @Path("empresa/{nome}/leiloes")
    public LeiloesRep getLeiloes(@PathParam("nome") String nome) {
        String nomeEmp = null;

        for(String key : empresas.keySet()) {
            if (key.equals(nome)) {
                nomeEmp = nome;
                break;
            }
        }

        if(nomeEmp == null){
            throw new WebApplicationException(404);
        }

        LeiloesRep leiRep = new LeiloesRep(this.empresas.get(nomeEmp).getLeiloes());

        return leiRep;
    }

    @GET
    @Path("empresa/{nome}/leilao/{id}")
    public LeilaoRep getLeilao(@PathParam("nome") String nome, @PathParam("id") int id) {
        String nomeEmp = null;
        int idL = 0;

        for(String key : empresas.keySet()) {
            if (key.equals(nome)) {
                nomeEmp = nome;
                break;
            }
        }

        if(nomeEmp != null) {
            for (Leilao leilao : empresas.get(nomeEmp).historicoLeiloes) {
                if (leilao.id == id) {
                    idL = id;
                    break;
                }
            }
        }

        if(nomeEmp == null || idL == 0){
            throw new WebApplicationException(404);
        }

        LeilaoRep leilao = new LeilaoRep(this.empresas.get(nomeEmp).getLeilao(id));

        return leilao;
    }
/*
    TODO Posts em comentário
    @POST
    @Path("empresa/{nome}/leilao/{id}/{taxaMaxima}/{montanteTotal}/{data}/{sucesso}/{licitacoes}")
    public Response put(@PathParam("nome") String nome, @PathParam("id") int id, @PathParam("taxaMaxima") int taxaMaxima,
                        @PathParam("montanteTotal") int montanteTotal, @PathParam("data") Date data, @PathParam("sucesso") boolean sucesso,
                        @PathParam("licitacoes") List<Licitacao> licitacoes) {

        empresas.get(nome).addLeilao(id, taxaMaxima, montanteTotal, nome, data, sucesso, licitacoes);

        return Response.status(201).build();
    }
*/
    @GET
    @Path("empresa/{nome}/emissoes")
    public EmissoesRep getEmissoes(@PathParam("nome") String nome) {
        String nomeEmp = null;

        for(String key : empresas.keySet()) {
            if (key.equals(nome)) {
                nomeEmp = nome;
                break;
            }
        }

        if(nomeEmp == null){
            throw new WebApplicationException(404);
        }

        EmissoesRep emRep = new EmissoesRep(empresas.get(nomeEmp).getEmissoes());

        return emRep;
    }

    @GET
    @Path("empresa/{nome}/emissao/{id}")
    public EmissaoRep getEmissao(@PathParam("nome") String nome, @PathParam("id") int id) {
        String nomeEmp = null;
        int idE = 0;

        for(String key : empresas.keySet()) {
            if (key.equals(nome)) {
                nomeEmp = nome;
                break;
            }
        }

        if(nomeEmp != null) {
            for (Emissao emissao : empresas.get(nomeEmp).historicoEmissoes) {
                if (emissao.id == id) {
                    idE = id;
                    break;
                }
            }
        }

        if(nomeEmp == null || idE == 0){
            throw new WebApplicationException(404);
        }

        EmissaoRep emissao = new EmissaoRep(empresas.get(nomeEmp).getEmissao(id));

        return emissao;
    }
/*
    @POST
    @Path("empresa/{nome}/emissao/{id}/{taxa}/{montanteTotal}/{sucesso}/{licitacoes}")
    public Response put(@PathParam("nome") String nome, @PathParam("id") int id, @PathParam("taxa") int taxa,
                        @PathParam("montanteTotal") int montanteTotal, @PathParam("sucesso") boolean sucesso,
                        @PathParam("licitacoes") List<Licitacao> licitacoes) {

        empresas.get(nome).addEmissao(id, taxa, montanteTotal, nome, sucesso, licitacoes);

        return Response.status(201).build();
    }
*/
    @GET
    @Path("empresa/{nome}/leilao/{id}/licitacoes")
    public LicitacoesRep getLicitacoesLeilao(@PathParam("nome") String nome, @PathParam("id") int id) {
        String nomeEmp = null;
        int idL = 0;

        for(String key : empresas.keySet()) {
            if (key.equals(nome)) {
                nomeEmp = nome;
                break;
            }
        }

        if(nomeEmp != null) {
            for (Leilao leilao : empresas.get(nomeEmp).historicoLeiloes) {
                if (leilao.id == id) {
                    idL = id;
                    break;
                }
            }
        }

        if(nomeEmp == null || idL == 0){
            throw new WebApplicationException(404);
        }

        LicitacoesRep licit = new LicitacoesRep(empresas.get(nomeEmp).getLeilao(idL).getLicitacoes());

        return licit;
    }

    @GET
    @Path("empresa/{nome}/emissao/{id}/licitacoes")
    public LicitacoesRep getLicitacoesEmissao(@PathParam("nome") String nome, @PathParam("id") int id) {
        String nomeEmp = null;
        int idE = 0;

        for(String key : empresas.keySet()) {
            if (key.equals(nome)) {
                nomeEmp = nome;
                break;
            }
        }

        if(nomeEmp != null) {
            for (Emissao emissao : empresas.get(nomeEmp).historicoEmissoes) {
                if (emissao.id == id) {
                    idE = id;
                    break;
                }
            }
        }

        if(nomeEmp == null || idE == 0){
            throw new WebApplicationException(404);
        }

        LicitacoesRep licit = new LicitacoesRep(empresas.get(nomeEmp).getLeilao(idE).getLicitacoes());

        return licit;
    }

    @GET
    @Path("empresa/{nome}/leilao/{idL}/licitacao/{id}")
    public LicitacaoRep getLicitacaoLeilao(@PathParam("nome") String nome, @PathParam("idL") int idL, @PathParam("id") int id) {
        String nomeEmp = null;
        int idLeilao = 0;
        int idLic = 0;

        for(String key : empresas.keySet()) {
            if (key.equals(nome)) {
                nomeEmp = nome;
                break;
            }
        }

        if(nomeEmp != null) {
            for (Emissao emissao : empresas.get(nomeEmp).historicoEmissoes) {
                if (emissao.id == idL) {
                    idLeilao = idL;
                    break;
                }
            }
        }

        if(idLeilao != 0) {
            for (Licitacao lic : empresas.get(nomeEmp).getEmissao(idLeilao).licitacoes) {
                if (lic.id == id) {
                    idLic = id;
                    break;
                }
            }
        }

        if(nomeEmp == null || idLeilao == 0 || idLic == 0){
            throw new WebApplicationException(404);
        }

        LicitacaoRep licit = new LicitacaoRep(empresas.get(nomeEmp).getLeilao(idLeilao).getLicitacao(idLic));

        return licit;
    }

    @GET
    @Path("empresa/{nome}/emissao/{idE}/licitacao/{id}")
    public LicitacaoRep getLicitacaoEmissao(@PathParam("nome") String nome, @PathParam("idE") int idE, @PathParam("id") int id) {
        String nomeEmp = null;
        int idEmissao = 0;
        int idLic = 0;

        for(String key : empresas.keySet()) {
            if (key.equals(nome)) {
                nomeEmp = nome;
                break;
            }
        }

        if(nomeEmp != null) {
            for (Emissao emissao : empresas.get(nomeEmp).historicoEmissoes) {
                if (emissao.id == idE) {
                    idEmissao = idE;
                    break;
                }
            }
        }

        if(idEmissao != 0) {
            for (Licitacao lic : empresas.get(nomeEmp).getEmissao(idEmissao).licitacoes) {
                if (lic.id == id) {
                    idLic = id;
                    break;
                }
            }
        }

        if(nomeEmp == null || idE == 0 || idLic == 0){
            throw new WebApplicationException(404);
        }

        LicitacaoRep licit = new LicitacaoRep(empresas.get(nomeEmp).getLeilao(idEmissao).getLicitacao(idLic));


        return licit;
    }
}