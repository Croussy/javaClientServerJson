package beans;


public class Acteur 
{
	private int codeActeur;
	private String nomActeur;
	private String prenomActeur;
	private Genre genre; 
	private int anneeNaiss;
	
	
	public Acteur(int codeActeur, String nomActeur, String prenomActeur,
			Genre genre, int anneeNaiss) {
		this.codeActeur = codeActeur;
		this.nomActeur = nomActeur;
		this.prenomActeur = prenomActeur;
		this.genre = genre;
		this.anneeNaiss = anneeNaiss;
	}

	public int getCodeActeur() 
	{
		return codeActeur;
	}

	public String getNomActeur()
	{
		return nomActeur;
	}
	
	public Genre getGenre() {
		return genre;
	}

	public String getPrenomActeur() 
	{
		return prenomActeur;
	}

	public int getAnneeNaiss()
	{
		return anneeNaiss;
	}

	@Override
	public String toString()
	{
		return codeActeur+". "+nomActeur +" "+prenomActeur;
	}
	
	
	
}
