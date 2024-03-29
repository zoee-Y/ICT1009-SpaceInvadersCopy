import java.awt.Color;
/**
 * This is my Shooter Object, 
 * it is essentially the player
 * @author elan
 *
 */
public class Shooter extends Entity
{
	/**
	 * constructor for the shooter, 
	 * creates a shooter at the middle bottom of the screen
	 */
	public Shooter()
	{
		super(States.SHOOTER_START_X, States.SHOOTER_START_Y, 0);
		HP = States.playerHP;
	}
	
	//add parameters to specify starting position
	public Shooter(double x, double y)
	{
		super(x, y, 0);
		//changed for player 2 HP
		HP = States.player2HP;
	}

	/**
	 * updates the shooter
	 */
	public void update()
	{
		checkCollision();
	}

	/**
	 * check what the shooter has come into contact with
	 * and handles it
	 */
	private void checkCollision()
	{
		for(int i = GameObjectManager.enemyBullets.size() - 1; i >= 0; i--) {
			if(inRadius(GameObjectManager.player, GameObjectManager.enemyBullets.get(i)))
			{
				collidePlayerAndEnemyBullet(GameObjectManager.enemyBullets.get(i), GameObjectManager.player);
				break;
			}
			else if (inRadius(GameObjectManager.player2, GameObjectManager.enemyBullets.get(i))) {
				collidePlayerAndEnemyBullet(GameObjectManager.enemyBullets.get(i), GameObjectManager.player2);
				break;
			}
		}
	}

	/**
	 * collides the player with the enemy's bullet, 
	 * destroyes the bullet and if the player is out of health restarts the level
	 * @param enemyBullet made contact with
	 */
	//added param for whether it is player 1 or two that got hit
	private void collidePlayerAndEnemyBullet(EnemyBullet enemyBullet, Shooter shooter)
	{
		try
		{
			GameObjectManager.enemyBullets.remove(enemyBullet.getIndex());
		}
		catch(IndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}

		if(shooter.damage(enemyBullet))
			GameObjectManager.loseLevel();
	}

	/**
	 * Causes the shooter to shoot Bullets
	 */
	//added parameter shooter object, to check if its player 1 or 2 shooting
	public void shoot(Shooter shooter)
	{
		//if shooter is player 1, use canShoot
		if (shooter == GameObjectManager.player) {
			if(States.canShoot && !States.pause)
			{
				if(States.numberBulletsPlayerShoots % 2 != 0)
				{
					createBullet(shooter);
	
					for(int j = 1; j <= States.numberBulletsPlayerShoots/2; j++)
					{
						createBullet(shooter, 5*j);
						createBullet(shooter, -5*j);
					}
				}
				else
				{
					for(int j = 1; j <= States.numberBulletsPlayerShoots/2; j++)
					{
						createBullet(shooter, 5*j -2.5);
						createBullet(shooter, -5*j + 2.5);
					}
				}
				States.canShoot = false;
				GameTimer.shootTimer.start();
				GameObjectManager.soundManager.playShoot();
			}
		}
		//if shooter is player 2, use canShoot2
		else if (shooter == GameObjectManager.player2) {
			if(States.canShoot2 && !States.pause)
			{
				if(States.numberBulletsPlayerShoots % 2 != 0)
				{
					createBullet(shooter);
	
					for(int j = 1; j <= States.numberBulletsPlayerShoots/2; j++)
					{
						createBullet(shooter, 5*j);
						createBullet(shooter, -5*j);
					}
				}
				else
				{
					for(int j = 1; j <= States.numberBulletsPlayerShoots/2; j++)
					{
						createBullet(shooter, 5*j -2.5);
						createBullet(shooter, -5*j + 2.5);
					}
				}
				States.canShoot2 = false;
				GameTimer.shootTimer2.start();
				GameObjectManager.soundManager.playShoot();
			}
		}
	}

	/**
	 * creates a bullet at the shooters co-ordinates,
	 * with the same angle
	 */
	//added parameter to create bullets based on if player 1 or 2 is shooting
	private void createBullet(Shooter shooter)
	{
		try
		{
			//add set bullet type for current bullet, after changing Bullet() params
			
			if (shooter == GameObjectManager.player) {
				Bullet newBullet = new Bullet(shooter, GameObjectManager.bullets.size(), "red");
				GameObjectManager.bullets.add(GameObjectManager.bullets.size(), newBullet);
			}
			else if (shooter == GameObjectManager.player2) {
				Bullet newBullet = new Bullet(shooter, GameObjectManager.bullets.size(), "blue");
				GameObjectManager.bullets.add(GameObjectManager.bullets.size(), newBullet);
			}
						
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * creates a bullet at the shooters co-ordinates,
	 * with a slight angle offset
	 */
	public void createBullet(Shooter shooter, double angle)
	{
		try 
		{
			//add set bullet type for current bullet, after changing Bullet() params
			
			if (shooter == GameObjectManager.player) {
				Bullet newBullet = new Bullet(shooter, GameObjectManager.bullets.size(), angle, "red");
				GameObjectManager.bullets.add(GameObjectManager.bullets.size(), newBullet);
			}
			else if (shooter == GameObjectManager.player2) {
				Bullet newBullet = new Bullet(shooter, GameObjectManager.bullets.size(), angle, "blue");
				GameObjectManager.bullets.add(GameObjectManager.bullets.size(), newBullet);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @return number of lives the shooter has left
	 */
	public int getLives()
	{
		return States.playerLives[0];
	}

	/**
	 * reduces the number of lives the shooter has
	 */
	public void loseLife()
	{
		States.playerLives[0]--;
		GameObjectManager.loseLevel();
	}

	/**
	 * draws the shooter and its health bar on the screen
	 */
	public void render()
	{
		double HPBarWidth = States.WINDOW_RESOLUTION/(double)4;
		double HPBarHeight = States.WINDOW_RESOLUTION/(double)20;

		StdDraw.setPenColor(Color.RED);
		StdDraw.filledRectangle(States.WINDOW_RESOLUTION - HPBarWidth/(double)1.5, States.WINDOW_RESOLUTION - HPBarHeight/(double)1.5, HPBarWidth/(double)2, HPBarHeight/(double)2);
		StdDraw.setPenColor(Color.GREEN);
		StdDraw.filledRectangle(States.WINDOW_RESOLUTION - HPBarWidth/(double)1.5, States.WINDOW_RESOLUTION - HPBarHeight/(double)1.5, HPBarWidth/(double)2 - (1 - HP/(double)States.playerHP)*(HPBarWidth/(double)2), HPBarHeight/(double)2);

		StdDraw.setPenColor(Color.BLACK);
		StdDraw.text(States.WINDOW_RESOLUTION - HPBarWidth/(double)1.5, States.WINDOW_RESOLUTION - HPBarHeight/(double)1.5, ((int)(HP*100/(double)States.playerHP)) + "% x" + (States.playerLives[0]));

		super.render("shooter.png");
	}
	
	public void render1()
	{
		double HPBarWidth = States.WINDOW_RESOLUTION2/(double)4;
		double HPBarHeight = States.WINDOW_RESOLUTION2/(double)20;

		StdDraw.setPenColor(Color.RED);
		StdDraw.filledRectangle(States.WINDOW_RESOLUTION2 - HPBarWidth/(double)1.5, States.WINDOW_RESOLUTION2 - HPBarHeight/(double)1.5, HPBarWidth/(double)2, HPBarHeight/(double)2);
		StdDraw.setPenColor(Color.GREEN);
		StdDraw.filledRectangle(States.WINDOW_RESOLUTION2 - HPBarWidth/(double)1.5, States.WINDOW_RESOLUTION2 - HPBarHeight/(double)1.5, HPBarWidth/(double)2 - (1 - HP/(double)States.player2HP)*(HPBarWidth/(double)2), HPBarHeight/(double)2);

		StdDraw.setPenColor(Color.BLACK);
		StdDraw.text(States.WINDOW_RESOLUTION2 - HPBarWidth/(double)1.5, States.WINDOW_RESOLUTION2 - HPBarHeight/(double)1.5, ((int)(HP*100/(double)States.player2HP)) + "% x" + (States.playerLives2[0]));

		super.render("shooter2.png");			
	}

	/**
	 * sets the shooters angle when using the mouse
	 */
	public void setAngle()
	{
		if(Screen.mouse.getX() <= xPos)
			angle = Math.toDegrees(Math.atan((yPos - Screen.mouse.getY())/(double)(xPos - Screen.mouse.getX()))) + 90;
		else
			angle = Math.toDegrees(Math.atan((yPos - Screen.mouse.getY())/(double)(xPos - Screen.mouse.getX()))) - 90;

		if(angle > 90)
			angle = 90;
		else if(angle < -90)
			angle = -90;
	}

	/**
	 * @param health sets the shooters hit points to this value
	 */
	public void setHP(int health)
	{
		HP = health;
	}
}