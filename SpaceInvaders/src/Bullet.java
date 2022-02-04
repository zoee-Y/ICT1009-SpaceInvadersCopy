/**
 * This Class can be used to create bullet objects
 * @author elan
 *
 */
public class Bullet extends Entity
{
	/**
	 * phasable is final and private as it is determined when the object is created and never altered
	 * and should not be seen by other classes
	 */
	private final boolean phasable = determinePhasability();
	
	//add bulletType
	private String bulletType;

	/**
	 * Cunstructor for the Bullet object
	 * @param entity Bullet will be created with same x, y, and angle as this entity
	 * @param index Give the Bullet a unique index to identify it
	 */
	//added param to Bullet constructor to set bulletType
	public Bullet(Entity entity, int index, String bulletType)
	{
		super(entity.getX(), entity.getY(), entity.getAngle(), index);
		this.bulletType = bulletType;
	}

	/**
	 * Cunstructor for the Bullet object
	 * @param entity Bullet will be created with same x, y, and angle as this entity
	 * @param index Give the Bullet a unique index to identify it
	 * @param angleDeviation this angle will be subtracted from the entity's angle and the bullet will be created with the resulting angle
	 */
	public Bullet(Entity entity, int index, double angleDeviation, String bulletType)
	{
		super(entity.getX(), entity.getY(), entity.getAngle() - angleDeviation, index);
		this.bulletType = bulletType;
	}

	/**
	 * Does all nessisary updates for the Bullet object
	 */
	public void update()
	{
		updatePos();
		checkBounds();
	}

	/**
	 * updates the Bullets x and y co-ordinates
	 */
	protected void updatePos()
	{
		moveLeft(States.BULLET_SPEED*Math.cos(Math.toRadians(90 - angle)));
		moveUp(States.BULLET_SPEED*Math.sin(Math.toRadians(90 - angle)));
	}

	/**
	 * draws the Bullet on the screen
	 */
	public void render()
	{
		String type = "";

		if(States.playerDamage > 23)
			type = "bullet4.png";
		else if(States.playerDamage > 18)
			type = "bullet3.png";
		else if(States.playerDamage > 13)
			type = "bullet2.png";
		else
			type = "bullet1.png";

		StdDraw.picture(xPos, yPos, type, States.BULLET_SIZE, States.BULLET_SIZE, angle);
	}

	/**
	 * checks if the bullet is still on the screen, if it is not it is destroyed
	 */
	private void checkBounds() 
	{
		if(!(canMoveLeft() && canMoveRight() && canMoveUp() && canMoveDown()))
		{
			try
			{
				GameObjectManager.bullets.remove(index);
			}
			catch(IndexOutOfBoundsException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns true if Bullet can move left
	 */
	protected boolean canMoveLeft()
	{
		if(xPos <= 0)
			return false;

		return true;
	}

	/**
	 * Returns true if Bullet can move right
	 */
	protected boolean canMoveRight()
	{
		if(xPos >= States.WINDOW_RESOLUTION)
			return false;

		return true;
	}

	/**
	 * Returns true if Bullet can move down
	 */
	protected boolean canMoveDown()
	{
		if(yPos <= 0)
			return false;

		return true;
	}

	/**
	 * Returns how much damage this bullet does
	 * @return damage of the bullet
	 */
	public int getDamage()
	{
		return States.playerDamage;
	}

	/**
	 * Determines if this bullet is allowed to pass through the bunker without causing damage to it
	 * @return true if bullet may pass through the bunker
	 */
	private boolean determinePhasability()
	{
		if(!(this instanceof EnemyBullet || this instanceof HomingBullet) && (int)(Math.random()*10 + 1) > 10 - 1.5*States.bulletPhaseChance)//gives the bullet a chance to phase through the bunker
		{
			return true;
		}

		return false;
	}

	/**
	 * Returns if bullet is phasable
	 * @return true if bullet is phasable
	 */
	public boolean isPhasable()
	{
		return phasable;
	}
	
	//add getter and setter for bulletType
	public String getBulletType() {
		return this.bulletType;
	}
	
	public void setBulletType(String bulletType) {
		this.bulletType = bulletType;
	}
}