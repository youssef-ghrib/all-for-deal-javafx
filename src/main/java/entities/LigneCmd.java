package entities;

public class LigneCmd {

    private int qte;

    private Commande cmd;
    private Produit produit;

    public LigneCmd() {
    }

    public LigneCmd(int qte, Commande cmd, Produit produit) {
        this.qte = qte;
        this.cmd = cmd;
        this.produit = produit;
    }

    @Override
    public String toString() {
        return "LigneCmd{" + "qte=" + qte + ", cmd=" + cmd + ", produit=" + produit + '}';
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public Commande getCmd() {
        return cmd;
    }

    public void setCmd(Commande cmd) {
        this.cmd = cmd;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

}
