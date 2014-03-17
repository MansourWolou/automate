import java.util.Stack;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/**
 * Base de chaque noeud d'un arbre d'une expression rationnelle
 */
public abstract class Arbre {

	/**
	 * Symbole contatenation
	 */
	public static final char SYMBOLE_CONCAT = '.';
	
	/**
	 * Symbole ou
	 */
	public static final char SYMBOLE_OU = '+';
	
	/**
	 * Symbole du mot vide
	 */
	public static final char MOT_VIDE = '1';

	/**
	 * Symbole du noeud
	 */
	public char symbole;

	/**
	 * Vrai si le sous arbre du noeud contient le mot vide
	 */
	public boolean contientMotVide;

	/**
	 * Premiers symboles du sous arbre du noeud
	 */
	public Set<Feuille> premiers;

	/**
	 * Derniers symboles du sous arbre du noeud
	 */
	public Set<Feuille> derniers;

	/**
	 * Successeurs (transition) du sous arbre du noeud
	 */
	public abstract Map<Feuille, Set<Feuille>> succ();

	/**
	 * Conversion d'une expression rationnelle ecrite en lecture postfixe en arbre
	 * @param expr expression rationnelle
	 * @return l'arbre de l'expression rationnelle
	 */
	public static Arbre lirePostfixe(String expr){
		Stack<Arbre> pile = new Stack<Arbre>();
		
		for(int i = 0; i < expr.length(); i++){
			char valeur = expr.charAt(i);
			if(valeur == '*'){
				Arbre element = pile.pop();
				Unaire unaire = new Unaire(element);
				pile.push(unaire);
			}else if(valeur == SYMBOLE_CONCAT || valeur == SYMBOLE_OU){
				Arbre element1 = pile.pop();
				Arbre element2 = pile.pop();
				Binaire binaire = new Binaire(valeur, element2, element1);
				pile.push(binaire);
			}else{
				Feuille f = new Feuille(valeur);
				pile.push(f);
			}
		}

		return pile.pop();
	}
}
