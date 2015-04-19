package simran_preet.com.funfacts;

import java.util.Random;

/**
 * Created by jc on 12/20/14.
 */
public class FactColors
{

    private static final String TAG = "FactColors";
    private static FactColors instance = null;

    public static FactColors getInstance()
    {
        if (instance == null) instance = new FactColors();
        return instance;
    }

    private static String[] colors = {"#39add1",
            "#3079ab",
            "#c25975",
            "#e15258",
            "#f9845b",
            "#838cc7",
            "#7d669e",
            "#53bbb4",
            "#51b46d",
            "#e0ab18",
            "#637a91",
            "#f092b0",
            "#b7c0c7",
            "#ffcf17",
            "#9d7f6f",
            "#9289ba",
            "#238341",
            "#33446a",
            "#3b4b98",
            "#f44336",
            "#E91E63",
            "#9C27B0",
            "#673AB7",
            "#3F51B5",
            "#2196F3",
            "#03A9F4",
            "#00BCD4",
            "#4CAF50",
            "#8BC34A"
    };


    private static String[] getColors()
    {
        return colors;
    }

    public static String getRandomColor()
    {
        String color;
        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(colors.length);
        color = getColors()[randomNumber];
        return color;
    }


}
