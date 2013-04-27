/**
 * The engine interface. Implemented by the suggestion enginer.
 * @author hacheson
 */
package autocorrect;

import java.util.List;

public interface Engine {
	public void setText(String text);
	public List<String> suggest();
}
