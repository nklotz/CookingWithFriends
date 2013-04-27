/**
 * This is the Generator Interface. It serves as a model to generate
 */

package autocorrect;

import java.util.List;

public interface Generator {
	public List<String> generate();
	public void setText(String text);
}
