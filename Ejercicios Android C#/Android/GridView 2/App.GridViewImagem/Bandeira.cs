namespace App.GridViewImagem
{
    public class Bandeira
    {
        //private String Nome;
        //private int Imagem;

        public Bandeira(string _nome, int _imagem)
        {
            this.Nome = _nome;
            this.Imagem = _imagem;
        }

        public string Nome { get; set; }
        public int Imagem { get; set; }
    }
}