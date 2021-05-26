package com.medusa.basemall.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author whh
 */
@ServerEndpoint("/websocket/placeanorder/notify/{appmodelId}")
@Component
public class PlaceAnOrderNotify {


	/**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
	private int onlineCount = 0;


	/**
	 * concurrent包的线程安全map，用来存放每个商家下的多个客户端对应的PlaceAnOrderNotify对象。
	 *一个商家可能会有多个后台,需要群发提醒
	 * 1-1-n
	 */
	public static Map<String, Map<String, PlaceAnOrderNotify>> merchant = new ConcurrentHashMap<>(16);

	/**
	 * 与某个客户端的连接会话，需要通过它来给客户端发送数据
	 * */
	private Session session;

	private static final Logger log = LoggerFactory.getLogger(PlaceAnOrderNotify.class);

	/**
	 * 连接建立成功调用的方法
	 * @param session
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("appmodelId") String appmodelId) {
		Map<String, PlaceAnOrderNotify> placeAnOrderNotifyMap = merchant.get(appmodelId);
		if (placeAnOrderNotifyMap == null) {
			placeAnOrderNotifyMap = new ConcurrentHashMap<>();
		}
		this.session = session;
		//加入商家店铺session
		placeAnOrderNotifyMap.put(session.getId(), this);
		merchant.put(appmodelId, placeAnOrderNotifyMap);
		log.info(appmodelId + "商家打开个数为:" + placeAnOrderNotifyMap.size());
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose(Session session, @PathParam("appmodelId") String appmodelId) throws IOException {
		Map<String, PlaceAnOrderNotify> placeAnOrderNotifyMap = merchant.get(appmodelId);
		placeAnOrderNotifyMap.remove(session.getId());
		session.close();
		log.info("有连接关闭。当前在线人数为：" + placeAnOrderNotifyMap.size());
	}

	/**
	 * 收到客户端消息后调用
	 * @param message
	 * @param appmodelId
	 */
	@OnMessage
	public void onMessage(String message, @PathParam("appmodelId") String appmodelId) {
		log.info("客户端发送的消息：" + message);
		sendAll(message, appmodelId);
	}

	/**
	 * 群发
	 * @param message
	 * @param appmodelId
	 */
	public static void sendAll(String message, String appmodelId) {
		Map<String, PlaceAnOrderNotify> placeAnOrderNotifyMap = merchant.get(appmodelId);
		if (placeAnOrderNotifyMap != null && placeAnOrderNotifyMap.size() > 0) {
			List<PlaceAnOrderNotify> list = placeAnOrderNotifyMap.values().stream().collect(Collectors.toList());
			try {
				for (PlaceAnOrderNotify placeAnOrderNotify : list) {
					placeAnOrderNotify.session.getBasicRemote().sendText("商家:" + appmodelId + "有新的订单生成,订单号:" + message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 发生错误时调用
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		log.info("有异常啦");
		error.printStackTrace();
	}


}
