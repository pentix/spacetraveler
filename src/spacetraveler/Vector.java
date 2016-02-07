package spacetraveler;

import org.jsfml.system.Vector2f;

public class Vector {
	public double x,y;
	
	public Vector(){		
	}

	public Vector(double x, double y){		
		this.x=x;
		this.y=y;
	}

	public Vector(Vector2f v){
		this.x=v.x;
		this.y=v.y;
	}

	public double abs()
	{
		return abs(this);
	}
	
	public Vector2f toVector2f()
	{
		return new Vector2f((float)x,(float)y);
	}

	
	public void div(double f)
	{
		mul(1/f);
	}
	
	public static Vector div(Vector v, double f)
	{
		return mul(v, 1/f);
	}
	
	public void mul(double f)
	{
		Vector v=mul(this, f);
		this.x=v.x;
		this.y=v.y;
	}
	
	public static Vector mul (Vector v, double f)
	{
		return new Vector(v.x*f, v.y*f);
	}
	
	public void add(Vector v)
	{
		Vector _v=add(this, v);
		this.x=_v.x;
		this.y=_v.y;
	}
	
	public static Vector add(Vector v, Vector w)
	{
		return new Vector(v.x+w.x, v.y+w.y);
	}
	
	public void sub(Vector v)
	{
		add(mul(v,-1));
	}
	
	public static Vector sub(Vector v, Vector w)
	{
		return add(v,mul(w,-1));
	}
	
	/**
	 * @brief Hilfsfunktion zum Berechnen der Länge des Vectors
	 * @param v ein Vektor
	 * @return Länge des Vektors
	 */
	public static double abs(Vector v)
	{
		return Math.abs(Math.sqrt(v.x*v.x+v.y*v.y));
	}
	
	/** 
	 * @brief Hilfsfunktion zum berechnen des Skalarproduktes
	 * @param a erster Vektor
	 * @param b zweiter Vektor
	 * @return Skalarprodukt der Beiden Vektoren
	 */
	public static double scalar(Vector a, Vector b)
	{
		return a.x*b.x+a.y*b.y;
	}
	
	public static Vector unitVector(Vector f)
	{
		return Vector.div(f,f.abs());
	}

	public void toUnitVector()
	{
		div(abs());
	}
	
	@Override
	public Vector clone()
	{
		return new Vector(x,y);		
	}
}
