import java.awt.event.KeyEvent;

/**
 * Controls my Enemies movement and attacks
 * @see Enemy
 * @see Boss
 * @see EnemyBullet
 * @see HomingBullet
 * @author elan
 *
 */
public class AI 
{
	/**
	 * Spawns My Enemies at the beginning of each level, 
	 * Checks fields in the Level class to determine which enemies to spanw and how many
	 */
	public static void initializeEnemies()
	{
		if(Level.getLevel() % 5 == 0)
			GameObjectManager.enemies.add(new Boss(Level.getLevel()));
		else
		{
			States.enemySize = States.WINDOW_CENTRE/(double)(Level.getEnemyCols() + 1);

			for(int x = 0; x < Level.getEnemyCols(); x++) {
				for(int y = 0; y < Level.getEnemyRows(); y++) {
					if((int)(Math.random()*(Level.getEnemyCols()*Level.getEnemyRows())/2) >= (Level.getEnemyCols()*Level.getEnemyRows())/1.8 - Level.getLevel())
						GameObjectManager.enemies.add(new LockOnEnemy(States.enemySize * 0.6 + x*(States.enemySize + States.enemySize*0.1), States.WINDOW_RESOLUTION - States.enemySize *1.5 - y*(States.enemySize + States.enemySize*0.1), 0, GameObjectManager.enemies.size(), States.enemyHealth));
					else
						GameObjectManager.enemies.add(new Enemy(States.enemySize * 0.6 + x*(States.enemySize + States.enemySize*0.1), States.WINDOW_RESOLUTION - States.enemySize *1.5 - y*(States.enemySize + States.enemySize*0.1), 0, GameObjectManager.enemies.size(), States.enemyHealth));
				}
			}
		}
	}

	/**
	 * moves all enemies Right
	 */
	private static void moveAllEnemiesRight()
	{
		for (int i = 0; i < GameObjectManager.enemies.size(); i++) 
			if(StdDraw.isKeyPressed(KeyEvent.VK_V))//a
			{
			GameObjectManager.enemies.get(i).moveRight(States.enemySpeed);
	}
	}
	/**
	 * moves all enemies Left
	 */
	private static void moveAllEnemiesLeft()
	{
		for (int i = 0; i < GameObjectManager.enemies.size(); i++) 
			if(StdDraw.isKeyPressed(KeyEvent.VK_C))//a
			{
			GameObjectManager.enemies.get(i).moveLeft(States.enemySpeed);
	}
	}
	/**
	 * moves all enemies Down
	 */
	private static void moveAllBasicEnemiesDown()
	{
		for (int i = 0; i < GameObjectManager.enemies.size(); i++) 
			GameObjectManager.enemies.get(i).moveDown(States.enemySpeed * 0.7);
	}

	/**
	 * Decides which Enemies are on the screen and calls the applicable AI function to control them
	 */
	public static void enemyAI()
	{
		if(Level.getLevel() % 5 == 0)
			bossAI();
		else
			basicEnemyAI();
	}
	
	/**
	 * Controls the movement of all basic enemies,
	 * i.e. making them move from one side of the screen to the other
	 * and down every time the reach a side
	 * @see Enemy
	 */
	public static void basicEnemyAI()
	{	
		if(!Enemy.isMovingDown())
		{
			if(Enemy.isMovingRight())
				moveAllEnemiesRight();
			if(Enemy.isMovingRight())
				moveAllEnemiesLeft();
		
			
			

			//edge detection, if an enemy is on the screen border: change direction
			
		}
		else
		{
			
			
		}
	}

	/**
	 * Causes a random enemy on the screen to shoot
	 * @see EnemyBullet
	 * @see HomingBullet
	 */
	public static void basicEnemyShoot()
	{
		if(GameObjectManager.enemies.size() > 0)
		{
			int enemyIndex = (int) (Math.random()*GameObjectManager.enemies.size());
			if((int)(Math.random() * (40 - Level.getLevel())) <= Level.getLevel())
				GameObjectManager.enemyBullets.add(new HomingBullet(GameObjectManager.enemies.get(enemyIndex), GameObjectManager.enemyBullets.size()));
			else
				GameObjectManager.enemyBullets.add(new EnemyBullet(GameObjectManager.enemies.get(enemyIndex), GameObjectManager.enemyBullets.size()));
		}
	}
	
	/**
	 * Causes the Boss to activate his shoot method
	 * @see Boss
	 */
	public static void bossShoot()
	{
		for(int i = 0; i < GameObjectManager.enemies.size(); i++)
			((Boss) GameObjectManager.enemies.get(i)).shoot();
	}
	
	/**
	 * Controls the movement of the Bosses,
	 * i.e. making them move from one side of the screen to the other
	 * and Rotate
	 * @see Boss
	 */
	public static void bossAI()
	{
		if(Enemy.isMovingRight())
			moveAllEnemiesRight();
		else
			moveAllEnemiesLeft();

		Enemy.IncreaseHorizontalDisplacement(States.enemySpeed);
		
		for (int i = 0; i < GameObjectManager.enemies.size(); i++) 
		{
			if(!GameObjectManager.enemies.get(i).canMoveLeft() || !GameObjectManager.enemies.get(i).canMoveRight())
			{
				Enemy.invertHorizontalMotion();
				Enemy.resetHorizontalDisplacement();
				bossAI();
				break;
			}
		}
		
		if(Level.getLevel() <= 10)	{}
		else if(Level.getLevel() <= 15)
		{
			if(Boss.isRotatingClock())
			{
				rotateBossClock();
			}
			else
			{
				rotateBossAntiClock();
			}
			Boss.increaseRotationalDisplacement(States.enemySpeed);
			
			if(Boss.getRotationalDisplacement() > 90)
			{
				Boss.invertRotation();
				Boss.resetRotationalDisplacement();
			}
		}
		else
		{
			lockBossOntoPlayer();
		}
	}
	
	/**
	 * Rotates the Boss clockwise
	 */
	private static void rotateBossClock()
	{
		for (int i = 0; i < GameObjectManager.enemies.size(); i++) 
			GameObjectManager.enemies.get(i).rotateClockWise(States.enemySpeed);
	}
	
	/**
	 * Rotates the Boss anti-clockwise
	 */
	private static void rotateBossAntiClock()
	{
		for (int i = 0; i < GameObjectManager.enemies.size(); i++) 
			GameObjectManager.enemies.get(i).rotateAntiClockWise(States.enemySpeed);
	}
	
	/**
	 * Causes the Boss to "Lock onto" the player, 
	 * i.e. always aim directly at the shooter object
	 */
	private static void lockBossOntoPlayer()
	{
		for (int i = 0; i < GameObjectManager.enemies.size(); i++) 
			((Boss) GameObjectManager.enemies.get(i)).lockOntoPlayer();
	}
}
