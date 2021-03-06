package upsimulator.interfaces;

import upsimulator.exceptions.TimesException;
import upsimulator.exceptions.TunnelNotExistException;
import upsimulator.exceptions.UnknownMembraneClassException;

public interface Result extends Cloneable {
	/**
	 * Set the result to target membrane
	 * 
	 * @param membrane
	 *            target membrane
	 * @param times
	 *            set result times
	 * @throws UnknownMembraneClassException
	 *             if membrane class can not be found while creating an instance
	 */
	public void setResult(Membrane membrane, int times) throws UnknownMembraneClassException, TimesException;

	/**
	 * Deep clone a result
	 * 
	 * @return the cloned result
	 */
	public Result deepClone();

	/**
	 * Select which tunnel this result will go through
	 * 
	 * @param current
	 *            current membrane
	 * @return the tunnel selected. If none has been selected, null will be
	 *         returned.
	 * @throws TunnelNotExistException
	 *             if tunnel doesn't exist and cannot be created
	 */
	public Tunnel selectTunnel(Membrane current) throws TunnelNotExistException;

}