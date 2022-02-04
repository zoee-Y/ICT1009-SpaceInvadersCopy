import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * This Class handles everything to do with time in my game, 
 * it controls the reload time for the player to shoot, 
 * it controls how long the game pauses for, etc
 * @author elan
 *
 */
public class GameTimer implements ActionListener
{
	/**
	 * some static timers, they are public because they are started and stopped from many different places
	 */
	public static Timer shootTimer;
	//add var for player 2 shootTimer
	public static Timer shootTimer2;
	public static Timer pauseTimer;
	public static Timer enemyFireTimer;
	public static Timer powerUpTimer;
	public static Timer powerUpSpawnTimer;

	private final String type;

	/**
	 * updates the timers to the new state values
	 */
	public static void updateTimers()
	{
		updateShootTimer();
		updateEnemyFireTimer();
		updatePowerUpTimer();
	}

	/**
	 * initializes the timers correctly
	 */
	public static void initializeTimers()
	{
		if(shootTimer != null)
		{
			shootTimer.stop();
			shootTimer = null;
		}
		shootTimer = new Timer(States.fireRate, new GameTimer("bullet"));
		
		if(shootTimer2 != null)
		{
			shootTimer2.stop();
			shootTimer2 = null;
		}
		shootTimer2 = new Timer(States.fireRate, new GameTimer("bullet2"));


		if(pauseTimer != null)
		{
			pauseTimer.stop();
			pauseTimer = null;
		}
		pauseTimer = new Timer(States.PAUSE_TIME, new GameTimer("pause"));


		if(enemyFireTimer != null)
		{
			enemyFireTimer.stop();
			enemyFireTimer = null;
		}
		enemyFireTimer = new Timer(States.enemyShootDelay, new GameTimer("enemyBullet"));


		if(powerUpTimer != null)
		{
			powerUpTimer.stop();
			powerUpTimer = null;
		}
		powerUpTimer = new Timer(States.powerUpDuration, new GameTimer("powerUp"));


		if(powerUpSpawnTimer != null)
		{
			powerUpSpawnTimer.stop();
			powerUpSpawnTimer = null;
		}
		powerUpSpawnTimer = new Timer(States.powerUpSpawnDelay, new GameTimer("powerUpSpawn"));
	}

	/**
	 * updates the fire rate timer
	 */
	public static void updateShootTimer()
	{
		if(shootTimer != null)
		{
			shootTimer.stop();
			shootTimer = null;
		}
		shootTimer = new Timer(States.fireRate, new GameTimer("bullet"));
		shootTimer.start();
		
		//add for player 2
		if(shootTimer2 != null)
		{
			shootTimer2.stop();
			shootTimer2 = null;
		}
		shootTimer2 = new Timer(States.fireRate, new GameTimer("bullet2"));
		shootTimer2.start();
	}

	public static void updateEnemyFireTimer()
	{
		enemyFireTimer.stop();
		enemyFireTimer = new Timer(States.enemyShootDelay, new GameTimer("enemyBullet"));
		enemyFireTimer.start();
	}

	public static void updatePowerUpTimer()
	{
		powerUpTimer.stop();
		powerUpTimer = new Timer(States.powerUpDuration, new GameTimer("powerUp"));
	}

	/**
	 * Constructor for the GameTimer class
	 * @param type determines what the timer will do
	 */
	public GameTimer(String type)
	{
		this.type = type;
	}

	/**
	 * depending on the type the timer will, 
	 * reload the shooters gun, 
	 * cause enemies to shoot, 
	 * activate and deactivate power ups, 
	 * pause and un-pause the game,
	 * spawn power ups
	 */
	public void actionPerformed(ActionEvent e)
	{
		if(type.equals("bullet"))
		{
			States.canShoot = true;
			shootTimer.stop();
		}
		//add for player 2
		else if(type.equals("bullet2"))
		{
			States.canShoot2 = true;
			shootTimer2.stop();
		}
		else if(type.equals("pause"))
		{
			States.pause = false;
			pauseTimer.stop();
			enemyFireTimer.start();
		}
		else if(type.equals("enemyBullet"))
		{
			if(Level.getLevel() % 5 == 0)
			{
				if(((int)(Math.random()*30)) >= 30 - 2*Level.getLevel())
					AI.bossShoot();
			}
			else
				if(((int)(Math.random()*30)) >= 25 - 1.8*Level.getLevel())
					AI.basicEnemyShoot();
		}
		else if(type.equals("powerUp"))
		{
			PowerUp.clearPowerUps();
		}
		else if(type.equals("powerUpSpawn"))
		{
			if(Level.getLevel() % 5 == 0)
			{
				if(((int)(Math.random()*100)) >= 80 - 2*Level.getLevel())
				{
					try
					{
						GameObjectManager.powerUpBoxes.add(new PowerUpBox(GameObjectManager.enemies.get((int)(Math.random()*GameObjectManager.enemies.size()))));
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}
			else
				if(((int)(Math.random()*100)) >= 90 - 1.8*Level.getLevel())
				{
					try 
					{
						GameObjectManager.powerUpBoxes.add(new PowerUpBox(GameObjectManager.enemies.get((int)(Math.random()*GameObjectManager.enemies.size()))));
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
		}
	}
}
