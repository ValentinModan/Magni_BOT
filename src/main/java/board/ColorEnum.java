package board;

import lombok.SneakyThrows;

public enum ColorEnum
{
    WHITE,
    BLACK;

    ColorEnum next(ColorEnum colorEnum)
    {
        return opposite(colorEnum);
    }

    @SneakyThrows
    ColorEnum opposite(ColorEnum colorEnum)
    {
        switch (colorEnum) {
            case WHITE:
                return BLACK;
            case BLACK:
                return WHITE;
            default:
                throw new Exception("Color supported exception");
        }
    }
}
