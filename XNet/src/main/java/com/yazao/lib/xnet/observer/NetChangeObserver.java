
package com.yazao.lib.xnet.observer;

public interface NetChangeObserver {

	/**
	 * when network connected callback
	 */
	 void onNetConnected(NetUtil.NetType type);

	/**
	 *  when network disconnected callback
	 */
	 void onNetDisConnect() ;
}
