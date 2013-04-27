/**
 * Ranker interfaces. Specifies behavior on ranking a list of suggestions.
 */
package autocorrect;

import java.util.ArrayList;
import java.util.List;

public interface Ranker {
	/**
	 * The method that ranks a list of suggestions.
	 * @param toRank The list to rank.
	 * @param isSmart Whether to rank using the smart specifications.
	 * @return A ranked list of suggestions.
	 */
	public List<String> rank(List<String> toRank, boolean isSmart);

	/**
	 * Sets the text of the given string.
	 * @param text The text of the string.
	 */
	public void setText(String text);
}
