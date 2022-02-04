/**
 * The visual representation of the power up
 * @author elan
 *
 */
public class PowerUpBox extends Entity
{
	/**
	 * Constructor for PowerUpBox
	 * @param enemy creates the power up box at the same place as this enemy
	 */
	public PowerUpBox(Enemy enemy)
	{
		super(enemy.getX(), enemy.getY(), 0, GameObjectManager.powerUpBoxes.size());
	}

	/**
	 * updates the powerUpBox
	 */
	public void update()
	{
		updatePos();
		checkCollision();

		if(dead)
		{
			try
			{
				GameObjectManager.powerUpBoxes.remove(index);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Updates the powerUpBox's position
	 */
	private void updatePos()
	{
		moveDown(States.enemySpeed * 2);
	}

	/**
	 * draws the popwerUpBox on the screen
	 */
	public void render()
	{
		super.render("powerUpBox.png");
	}

	/**
	 * checks the things the powerUpBox could come in contact with
	 */
	private void checkCollision()
	{

		for(int j = 0; j < GameObjectManager.bullets.size(); j++)
		{
			if(inRadius(GameObjectManager.bullets.get(j), GameObjectManager.powerUpBoxes.get(index)))
			{
				collideBulletAndPowerUpBox(j, index);
				break;
			}
		}
		
		//add or condition for player 2 collect powerups
		if(inRadius(GameObjectManager.player, GameObjectManager.powerUpBoxes.get(index)) || inRadius(GameObjectManager.player2, GameObjectManager.powerUpBoxes.get(index)))
		{
			collidePlayerAndPowerUpBox(index);
		}
	}

	/**
	 * Collides the PowerUpBox with a Bullet, 
	 * destroying both and activating a power Up
	 * @param bulletIndex
	 * @param powerUpBoxIndex
	 */
	private void collideBulletAndPowerUpBox(int bulletIndex, int powerUpBoxIndex)
	{
		if(GameObjectManager.powerUp == null)
			GameObjectManager.powerUp = new PowerUp();
		else
			GameObjectManager.powerUp.activate();

		dead = true;
		try
		{
			GameObjectManager.bullets.remove(bulletIndex);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Collides the PowerUpBox with a shooter, 
	 * destroying the powerUpBox and activating a power Up
	 * @param powerUpBoxIndex
	 */
	private void collidePlayerAndPowerUpBox(int powerUpBoxIndex)
	{
		if(GameObjectManager.powerUp == null)
			GameObjectManager.powerUp = new PowerUp();
		else
			GameObjectManager.powerUp.activate();

		dead = true;
	}

	/**
	 * @return true if the PowerUpBox can move down,
	 * if it cant it moves it back onto the screen
	 */
	protected boolean canMoveDown()
	{
		if(yPos <= States.SHOOTER_SIZE/(double)2)
		{
			if(yPos < States.SHOOTER_SIZE/(double)2)
				yPos = States.SHOOTER_SIZE/(double)2;
			return false;
		}
		return true;
	}
}
