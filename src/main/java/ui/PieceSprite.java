package ui;

import ui.shapes.Shapes;

public class PieceSprite extends Sprite {
    public PieceSprite() {
        super(Shapes.BasicQuad.Vertices, Shapes.BasicQuad.Indices);
    }
}
