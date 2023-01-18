package UIProject.Module.Element;

import java.util.List;

class Position{
    public int x;
    public int y;
}

class Size{
    public int width;
    public int height;
}

public class UIElement {

    public Position position;

    public Size size;

    public List<UIElement> children;
}
