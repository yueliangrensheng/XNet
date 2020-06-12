
package com.yazao.lib.net;

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
