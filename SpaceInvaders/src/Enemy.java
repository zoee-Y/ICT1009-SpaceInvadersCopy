import java.awt.Color;

/**
 * This class lets you create an Enemy object
 * @author elan
 *
 */
public class Enemy extends Entity
{
	/**
	 * these Class variables are here as they are the same for all enemies on the screen at any given time
	 * they must also change together and they are not final as they do change
	 */
	private static double verticalDisplacement = 0, horizontalDisplacement = 0;
	private static boolean movingDown = false, movingRight = true;
	//add variable enemyType
	private String enemyType = "";

	/**
	 * Constructor for my Enemy
	 * @param x x co-ordinate where it will be created
	 * @param y y co-ordinate where it will be created
	 * @param a angle it will start with
	 * @param i unique identifier
	 */
	public Enemy(double x, double y, double a, int i)
	{
		super(x, y, a, i);
	}

	/**
	 * Constructor for my Enemy
	 * @param x x co-ordinate where it will be created
	 * @param y y co-ordinate where it will be created
	 * @param a angle it will start with
	 * @param i unique identifier
	 * @param hp hit points it will start with
	 */
	public Enemy(double x, double y, double a, int i, int hp)
	{
		super(x, y, a, i, hp);
	}

	/**
	 * calls all updates that the Enemy needs to do
	 */
	public void update()
	{
		checkCollision();

		if(dead)
		{
			try
			{
				GameObjectManager.enemies.remove(index);
			}
			catch(IndexOutOfBoundsException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * checks if the Enemy has come into contact with the player or a bullet, or reached the bottom of the screen
	 */
	private void checkCollision()
	{	
		//add for player 2
		if(inRadius(GameObjectManager.player, GameObjectManager.enemies.get(index)) || inRadius(GameObjectManager.player2, GameObjectManager.enemies.get(index)))
		{
			GameObjectManager.gameOver();
		}

		if(GameObjectManager.enemies.get(index).getY() < States.enemySize/(double)1.95)
		{
			GameObjectManager.gameOver();
		}

		for (int j = 0; j < GameObjectManager.bullets.size(); j++) 
		{
			if(inRadius(GameObjectManager.bullets.get(j), GameObjectManager.enemies.get(index)))
			{
				collideBulletAndEnemy(j);
			}
		}
	}

	/**
	 * Collides an Enemy with a bullet and destroys the bullet, 
	 * if the enemy's hit points is 0 or less then it is also destroyed, 
	 * and power Up spawns with a random chance
	 * @param bulletIndex unique identifier of the bullet hit
	 */
	private void collideBulletAndEnemy(int bulletIndex)
	{
		//add checking if bullets hit the right enemy : player 1 - red, player 2 - blue OR enemy is boss
		if (GameObjectManager.bullets.get(bulletIndex).getBulletType().equals(GameObjectManager.enemies.get(index).getEnemyType()) || 
				GameObjectManager.enemies.get(index).getEnemyType().equals("boss")) {
			//moved this if block inside
			if(this.damage(GameObjectManager.bullets.get(bulletIndex)))
			{
				if(Level.powerUpChance())
					GameObjectManager.powerUpBoxes.add(new PowerUpBox(this));
				dead = true;
			}
		}
		
		try
		{
			GameObjectManager.bullets.remove(bulletIndex);
		}
		catch(IndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @return Returns true if Enemy can move left
	 */
	public boolean canMoveLeft()
	{
		if(xPos < States.enemySize*0.6)
			return false;

		return true;
	}

	/**
	 * @return Returns true if Enemy can move right
	 */
	public boolean canMoveRight()
	{
		if(xPos > States.WINDOW_RESOLUTION - States.enemySize*0.6)
			return false;

		return true;
	}

	/**
	 * Draws the Enemy
	 */
	public void render()
	{
		String type = "";
			

		if(States.enemyHealth > 23)
			type = "Enemy4.png";
		else if(States.enemyHealth > 18)
			type = "Enemy3.png";
		else if(States.enemyHealth > 13)
			type = "Enemy2.png";
		else
			type = "Enemy1.png";

		StdDraw.picture(xPos, yPos, type, States.enemySize, States.enemySize, angle);
	
		if(HP < States.enemyHealth)
			renderHPbar();
		
	}
	
	//added parameter here to set the type
	public void render(String type)
	{
		//String type = "";
		
		if(States.enemyHealth > 23)
			type += "Enemy4.png";
		else if(States.enemyHealth > 18)
			type += "Enemy3.png";
		else if(States.enemyHealth > 13)
			type += "Enemy2.png";
		else
			type += "Enemy1.png";

		StdDraw.picture(xPos, yPos, type, States.enemySize, States.enemySize, angle);
	
		if(HP < States.enemyHealth)
			renderHPbar();
		
	}

	/**
	 * draws the Enemy's Health bar
	 */
	public void renderHPbar()
	{
		double HPBarWidth = States.enemySize*0.7;
		double HPBarHeight = States.enemySize/(double)10;

		StdDraw.setPenColor(Color.RED);
		StdDraw.filledRectangle(xPos, yPos + States.enemySize/(double)2, HPBarWidth/(double)2, HPBarHeight/(double)2);
		StdDraw.setPenColor(Color.GREEN);
		StdDraw.filledRectangle(xPos, yPos + States.enemySize/(double)2, HPBarWidth/(double)2 - (1 - HP/(double)States.enemyHealth)*(HPBarWidth/(double)2), HPBarHeight/(double)2);
	}

	/**
	 * @return Returns the distance the Enemy has moved in this direction
	 */
	public static double getVerticalDisplacement()
	{
		return verticalDisplacement;
	}

	/**
	 * increases the vertical displacement 
	 * @param speed this is added to the vertical displacement
	 */
	public static void IncreaseVerticalDisplacement(double speed)
	{
		verticalDisplacement += speed;
	}

	/**
	 * resets the vertical displacement to 0
	 */
	public static void resetVerticalDisplacement()
	{
		verticalDisplacement = 0;
	}

	/**
	 * @return Returns the distance the Enemy has moved in this direction
	 */
	public static double getHorizontalDisplacement()
	{
		return horizontalDisplacement;
	}

	/**
	 * increases the horizontal displacement 
	 * @param speed this is added to the horizontal displacement
	 */
	public static void IncreaseHorizontalDisplacement(double speed)
	{
		horizontalDisplacement += speed;
	}

	/**
	 * resets the horizontal displacement to 0
	 */
	public static void resetHorizontalDisplacement()
	{
		horizontalDisplacement = 0;
	}

	/**
	 * @return Returns true if Enemy is busy moving down
	 */
	public static boolean isMovingDown()
	{
		return movingDown;
	}

	/**
	 * Starts enemy moving down if it was not, stops it if it was
	 */
	public static void startOrStopVerticalMotion()
	{
		movingDown = !movingDown;
	}

	/**
	 * @return Returns true if Enemy is moving Right
	 */
	public static boolean isMovingRight()
	{
		return movingRight;
	}

	/**
	 * Makes the enemy move in the opposite direction
	 */
	public static void invertHorizontalMotion()
	{
		movingRight = !movingRight;
	}
	
	//add getter and setter for enemyType
	public String getEnemyType() {
		return this.enemyType;
	}
	
	public void setEnemyType(String enemyType) {
		this.enemyType = enemyType;
	}
}
