package util;

final public class Tools
{
    static public double roundTo(double value, int decimalPlaces)
    {
        if (decimalPlaces < 0)
            return (value);
        
        double shift = Math.pow(10, decimalPlaces);
        
        return (Math.round(value * shift) / shift);
    }
}