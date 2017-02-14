package org.cybergarage.upnp;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import org.cybergarage.http.HTTPRequest;
import org.cybergarage.http.HTTPRequestListener;
import org.cybergarage.http.HTTPServerList;
import org.cybergarage.net.HostInterface;
import org.cybergarage.upnp.control.RenewSubscriber;
import org.cybergarage.upnp.device.DeviceChangeListener;
import org.cybergarage.upnp.device.Disposer;
import org.cybergarage.upnp.device.NotifyListener;
import org.cybergarage.upnp.device.SearchResponseListener;
import org.cybergarage.upnp.device.USN;
import org.cybergarage.upnp.event.EventListener;
import org.cybergarage.upnp.event.NotifyRequest;
import org.cybergarage.upnp.event.Property;
import org.cybergarage.upnp.event.PropertyList;
import org.cybergarage.upnp.event.SubscriptionRequest;
import org.cybergarage.upnp.event.SubscriptionResponse;
import org.cybergarage.upnp.ssdp.SSDPNotifySocketList;
import org.cybergarage.upnp.ssdp.SSDPPacket;
import org.cybergarage.upnp.ssdp.SSDPSearchRequest;
import org.cybergarage.upnp.ssdp.SSDPSearchResponseSocketList;
import org.cybergarage.util.Debug;
import org.cybergarage.util.ListenerList;
import org.cybergarage.util.Mutex;
import org.cybergarage.xml.Node;
import org.cybergarage.xml.NodeList;
import org.cybergarage.xml.ParserException;

public class ControlPoint implements HTTPRequestListener {
    private static final int DEFAULT_EVENTSUB_PORT = 8058;
    private static final String DEFAULT_EVENTSUB_URI = "/evetSub";
    private static final int DEFAULT_EXPIRED_DEVICE_MONITORING_INTERVAL = 60;
    private static final int DEFAULT_SSDP_PORT = 8008;
    private NodeList devNodeList;
    ListenerList deviceChangeListenerList;
    private Disposer deviceDisposer;
    private ListenerList deviceNotifyListenerList;
    private ListenerList deviceSearchResponseListenerList;
    private ListenerList eventListenerList;
    private String eventSubURI;
    private long expiredDeviceMonitoringInterval;
    private int httpPort;
    private HTTPServerList httpServerList;
    private Mutex mutex;
    private boolean nmprMode;
    private RenewSubscriber renewSubscriber;
    private int searchMx;
    private SSDPNotifySocketList ssdpNotifySocketList;
    private int ssdpPort;
    private SSDPSearchResponseSocketList ssdpSearchResponseSocketList;
    private Object userData;

    private SSDPNotifySocketList getSSDPNotifySocketList() {
        return this.ssdpNotifySocketList;
    }

    private SSDPSearchResponseSocketList getSSDPSearchResponseSocketList() {
        return this.ssdpSearchResponseSocketList;
    }

    static {
        UPnP.initialize();
    }

    public ControlPoint(int ssdpPort, int httpPort, InetAddress[] binds) {
        this.mutex = new Mutex();
        this.ssdpPort = 0;
        this.httpPort = 0;
        this.devNodeList = new NodeList();
        this.deviceNotifyListenerList = new ListenerList();
        this.deviceSearchResponseListenerList = new ListenerList();
        this.deviceChangeListenerList = new ListenerList();
        this.searchMx = 3;
        this.httpServerList = new HTTPServerList();
        this.eventListenerList = new ListenerList();
        this.eventSubURI = DEFAULT_EVENTSUB_URI;
        this.userData = null;
        this.ssdpNotifySocketList = new SSDPNotifySocketList(binds);
        this.ssdpSearchResponseSocketList = new SSDPSearchResponseSocketList(binds);
        setSSDPPort(ssdpPort);
        setHTTPPort(httpPort);
        setDeviceDisposer(null);
        setExpiredDeviceMonitoringInterval(60);
        setRenewSubscriber(null);
        setNMPRMode(false);
        setRenewSubscriber(null);
    }

    public ControlPoint(int ssdpPort, int httpPort) {
        this(ssdpPort, httpPort, null);
    }

    public ControlPoint() {
        this(DEFAULT_SSDP_PORT, DEFAULT_EVENTSUB_PORT);
    }

    public void finalize() {
        stop();
    }

    public void lock() {
        this.mutex.lock();
    }

    public void unlock() {
        this.mutex.unlock();
    }

    public int getSSDPPort() {
        return this.ssdpPort;
    }

    public void setSSDPPort(int port) {
        this.ssdpPort = port;
    }

    public int getHTTPPort() {
        return this.httpPort;
    }

    public void setHTTPPort(int port) {
        this.httpPort = port;
    }

    public void setNMPRMode(boolean flag) {
        this.nmprMode = flag;
    }

    public boolean isNMPRMode() {
        return this.nmprMode;
    }

    private void addDevice(Node rootNode) {
        this.devNodeList.add(rootNode);
    }

    private synchronized void addDevice(SSDPPacket ssdpPacket) {
        if (ssdpPacket.isRootDevice()) {
            Device dev = getDevice(USN.getUDN(ssdpPacket.getUSN()));
            if (dev != null) {
                dev.setSSDPPacket(ssdpPacket);
            } else {
                try {
                    Node rootNode = UPnP.getXMLParser().parse(new URL(ssdpPacket.getLocation()));
                    Device rootDev = getDevice(rootNode);
                    if (rootDev != null) {
                        rootDev.setSSDPPacket(ssdpPacket);
                        addDevice(rootNode);
                        performAddDeviceListener(rootDev);
                    }
                } catch (MalformedURLException me) {
                    Debug.warning(ssdpPacket.toString());
                    Debug.warning(me);
                } catch (ParserException pe) {
                    Debug.warning(ssdpPacket.toString());
                    Debug.warning(pe);
                }
            }
        }
    }

    private Device getDevice(Node rootNode) {
        if (rootNode == null) {
            return null;
        }
        Node devNode = rootNode.getNode("device");
        if (devNode != null) {
            return new Device(rootNode, devNode);
        }
        return null;
    }

    public DeviceList getDeviceList() {
        DeviceList devList = new DeviceList();
        int nRoots = this.devNodeList.size();
        for (int n = 0; n < nRoots; n++) {
            Device dev = getDevice(this.devNodeList.getNode(n));
            if (dev != null) {
                devList.add(dev);
            }
        }
        return devList;
    }

    public Device getDevice(String name) {
        int nRoots = this.devNodeList.size();
        for (int n = 0; n < nRoots; n++) {
            Device dev = getDevice(this.devNodeList.getNode(n));
            if (dev != null) {
                if (dev.isDevice(name)) {
                    return dev;
                }
                Device cdev = dev.getDevice(name);
                if (cdev != null) {
                    return cdev;
                }
            }
        }
        return null;
    }

    public boolean hasDevice(String name) {
        return getDevice(name) != null;
    }

    private void removeDevice(Node rootNode) {
        Device dev = getDevice(rootNode);
        if (dev != null && dev.isRootDevice()) {
            performRemoveDeviceListener(dev);
        }
        this.devNodeList.remove(rootNode);
    }

    protected void removeDevice(Device dev) {
        if (dev != null) {
            removeDevice(dev.getRootNode());
        }
    }

    protected void removeDevice(String name) {
        removeDevice(getDevice(name));
    }

    private void removeDevice(SSDPPacket packet) {
        if (packet.isByeBye()) {
            removeDevice(USN.getUDN(packet.getUSN()));
        }
    }

    public void removeExpiredDevices() {
        int n;
        DeviceList devList = getDeviceList();
        int devCnt = devList.size();
        Device[] dev = new Device[devCnt];
        for (n = 0; n < devCnt; n++) {
            dev[n] = devList.getDevice(n);
        }
        for (n = 0; n < devCnt; n++) {
            if (dev[n].isExpired()) {
                Debug.message("Expired device = " + dev[n].getFriendlyName());
                removeDevice(dev[n]);
            }
        }
    }

    public void setExpiredDeviceMonitoringInterval(long interval) {
        this.expiredDeviceMonitoringInterval = interval;
    }

    public long getExpiredDeviceMonitoringInterval() {
        return this.expiredDeviceMonitoringInterval;
    }

    public void setDeviceDisposer(Disposer disposer) {
        this.deviceDisposer = disposer;
    }

    public Disposer getDeviceDisposer() {
        return this.deviceDisposer;
    }

    public void addNotifyListener(NotifyListener listener) {
        this.deviceNotifyListenerList.add(listener);
    }

    public void removeNotifyListener(NotifyListener listener) {
        this.deviceNotifyListenerList.remove(listener);
    }

    public void performNotifyListener(SSDPPacket ssdpPacket) {
        int listenerSize = this.deviceNotifyListenerList.size();
        for (int n = 0; n < listenerSize; n++) {
            try {
                ((NotifyListener) this.deviceNotifyListenerList.get(n)).deviceNotifyReceived(ssdpPacket);
            } catch (Exception e) {
                Debug.warning("NotifyListener returned an error:", e);
            }
        }
    }

    public void addSearchResponseListener(SearchResponseListener listener) {
        this.deviceSearchResponseListenerList.add(listener);
    }

    public void removeSearchResponseListener(SearchResponseListener listener) {
        this.deviceSearchResponseListenerList.remove(listener);
    }

    public void performSearchResponseListener(SSDPPacket ssdpPacket) {
        int listenerSize = this.deviceSearchResponseListenerList.size();
        for (int n = 0; n < listenerSize; n++) {
            try {
                ((SearchResponseListener) this.deviceSearchResponseListenerList.get(n)).deviceSearchResponseReceived(ssdpPacket);
            } catch (Exception e) {
                Debug.warning("SearchResponseListener returned an error:", e);
            }
        }
    }

    public void addDeviceChangeListener(DeviceChangeListener listener) {
        this.deviceChangeListenerList.add(listener);
    }

    public void removeDeviceChangeListener(DeviceChangeListener listener) {
        this.deviceChangeListenerList.remove(listener);
    }

    public void performAddDeviceListener(Device dev) {
        int listenerSize = this.deviceChangeListenerList.size();
        for (int n = 0; n < listenerSize; n++) {
            ((DeviceChangeListener) this.deviceChangeListenerList.get(n)).deviceAdded(dev);
        }
    }

    public void performRemoveDeviceListener(Device dev) {
        int listenerSize = this.deviceChangeListenerList.size();
        for (int n = 0; n < listenerSize; n++) {
            ((DeviceChangeListener) this.deviceChangeListenerList.get(n)).deviceRemoved(dev);
        }
    }

    public void notifyReceived(SSDPPacket packet) {
        if (packet.isRootDevice()) {
            if (packet.isAlive()) {
                addDevice(packet);
            } else if (packet.isByeBye()) {
                removeDevice(packet);
            }
        }
        performNotifyListener(packet);
    }

    public void searchResponseReceived(SSDPPacket packet) {
        if (packet.isRootDevice()) {
            addDevice(packet);
        }
        performSearchResponseListener(packet);
    }

    public int getSearchMx() {
        return this.searchMx;
    }

    public void setSearchMx(int mx) {
        this.searchMx = mx;
    }

    public void search(String target, int mx) {
        getSSDPSearchResponseSocketList().post(new SSDPSearchRequest(target, mx));
    }

    public void search(String target) {
        search(target, 3);
    }

    public void search() {
        search("upnp:rootdevice", 3);
    }

    private HTTPServerList getHTTPServerList() {
        return this.httpServerList;
    }

    public void httpRequestRecieved(HTTPRequest httpReq) {
        if (Debug.isOn()) {
            httpReq.print();
        }
        if (httpReq.isNotifyRequest()) {
            NotifyRequest notifyReq = new NotifyRequest(httpReq);
            String uuid = notifyReq.getSID();
            long seq = notifyReq.getSEQ();
            PropertyList props = notifyReq.getPropertyList();
            int propCnt = props.size();
            for (int n = 0; n < propCnt; n++) {
                Property prop = props.getProperty(n);
                performEventListener(uuid, seq, prop.getName(), prop.getValue());
            }
            httpReq.returnOK();
            return;
        }
        httpReq.returnBadRequest();
    }

    public void addEventListener(EventListener listener) {
        this.eventListenerList.add(listener);
    }

    public void removeEventListener(EventListener listener) {
        this.eventListenerList.remove(listener);
    }

    public void performEventListener(String uuid, long seq, String name, String value) {
        int listenerSize = this.eventListenerList.size();
        for (int n = 0; n < listenerSize; n++) {
            ((EventListener) this.eventListenerList.get(n)).eventNotifyReceived(uuid, seq, name, value);
        }
    }

    public String getEventSubURI() {
        return this.eventSubURI;
    }

    public void setEventSubURI(String url) {
        this.eventSubURI = url;
    }

    private String getEventSubCallbackURL(String host) {
        return HostInterface.getHostURL(host, getHTTPPort(), getEventSubURI());
    }

    public boolean subscribe(Service service, long timeout) {
        if (service.isSubscribed()) {
            return subscribe(service, service.getSID(), timeout);
        }
        Device rootDev = service.getRootDevice();
        if (rootDev == null) {
            return false;
        }
        String ifAddress = rootDev.getInterfaceAddress();
        SubscriptionRequest subReq = new SubscriptionRequest();
        subReq.setSubscribeRequest(service, getEventSubCallbackURL(ifAddress), timeout);
        SubscriptionResponse subRes = subReq.post();
        if (subRes.isSuccessful()) {
            service.setSID(subRes.getSID());
            service.setTimeout(subRes.getTimeout());
            return true;
        }
        service.clearSID();
        return false;
    }

    public boolean subscribe(Service service) {
        return subscribe(service, -1);
    }

    public boolean subscribe(Service service, String uuid, long timeout) {
        SubscriptionRequest subReq = new SubscriptionRequest();
        subReq.setRenewRequest(service, uuid, timeout);
        if (Debug.isOn()) {
            subReq.print();
        }
        SubscriptionResponse subRes = subReq.post();
        if (Debug.isOn()) {
            subRes.print();
        }
        if (subRes.isSuccessful()) {
            service.setSID(subRes.getSID());
            service.setTimeout(subRes.getTimeout());
            return true;
        }
        service.clearSID();
        return false;
    }

    public boolean subscribe(Service service, String uuid) {
        return subscribe(service, uuid, -1);
    }

    public boolean isSubscribed(Service service) {
        if (service == null) {
            return false;
        }
        return service.isSubscribed();
    }

    public boolean unsubscribe(Service service) {
        SubscriptionRequest subReq = new SubscriptionRequest();
        subReq.setUnsubscribeRequest(service);
        if (!subReq.post().isSuccessful()) {
            return false;
        }
        service.clearSID();
        return true;
    }

    public void unsubscribe(Device device) {
        int n;
        ServiceList serviceList = device.getServiceList();
        int serviceCnt = serviceList.size();
        for (n = 0; n < serviceCnt; n++) {
            Service service = serviceList.getService(n);
            if (service.hasSID()) {
                unsubscribe(service);
            }
        }
        DeviceList childDevList = device.getDeviceList();
        int childDevCnt = childDevList.size();
        for (n = 0; n < childDevCnt; n++) {
            unsubscribe(childDevList.getDevice(n));
        }
    }

    public void unsubscribe() {
        DeviceList devList = getDeviceList();
        int devCnt = devList.size();
        for (int n = 0; n < devCnt; n++) {
            unsubscribe(devList.getDevice(n));
        }
    }

    public Service getSubscriberService(String uuid) {
        DeviceList devList = getDeviceList();
        int devCnt = devList.size();
        for (int n = 0; n < devCnt; n++) {
            Service service = devList.getDevice(n).getSubscriberService(uuid);
            if (service != null) {
                return service;
            }
        }
        return null;
    }

    public void renewSubscriberService(Device dev, long timeout) {
        int n;
        ServiceList serviceList = dev.getServiceList();
        int serviceCnt = serviceList.size();
        for (n = 0; n < serviceCnt; n++) {
            Service service = serviceList.getService(n);
            if (service.isSubscribed() && !subscribe(service, service.getSID(), timeout)) {
                subscribe(service, timeout);
            }
        }
        DeviceList cdevList = dev.getDeviceList();
        int cdevCnt = cdevList.size();
        for (n = 0; n < cdevCnt; n++) {
            renewSubscriberService(cdevList.getDevice(n), timeout);
        }
    }

    public void renewSubscriberService(long timeout) {
        DeviceList devList = getDeviceList();
        int devCnt = devList.size();
        for (int n = 0; n < devCnt; n++) {
            renewSubscriberService(devList.getDevice(n), timeout);
        }
    }

    public void renewSubscriberService() {
        renewSubscriberService(-1);
    }

    public void setRenewSubscriber(RenewSubscriber sub) {
        this.renewSubscriber = sub;
    }

    public RenewSubscriber getRenewSubscriber() {
        return this.renewSubscriber;
    }

    public boolean start(String target, int mx) {
        stop();
        int retryCnt = 0;
        int bindPort = getHTTPPort();
        HTTPServerList httpServerList = getHTTPServerList();
        while (!httpServerList.open(bindPort)) {
            retryCnt++;
            if (100 < retryCnt) {
                return false;
            }
            setHTTPPort(bindPort + 1);
            bindPort = getHTTPPort();
        }
        httpServerList.addRequestListener(this);
        httpServerList.start();
        SSDPNotifySocketList ssdpNotifySocketList = getSSDPNotifySocketList();
        if (!ssdpNotifySocketList.open()) {
            return false;
        }
        ssdpNotifySocketList.setControlPoint(this);
        ssdpNotifySocketList.start();
        int ssdpPort = getSSDPPort();
        retryCnt = 0;
        SSDPSearchResponseSocketList ssdpSearchResponseSocketList = getSSDPSearchResponseSocketList();
        while (!ssdpSearchResponseSocketList.open(ssdpPort)) {
            retryCnt++;
            if (100 < retryCnt) {
                return false;
            }
            setSSDPPort(ssdpPort + 1);
            ssdpPort = getSSDPPort();
        }
        ssdpSearchResponseSocketList.setControlPoint(this);
        ssdpSearchResponseSocketList.start();
        search(target, mx);
        Disposer disposer = new Disposer(this);
        setDeviceDisposer(disposer);
        disposer.start();
        if (isNMPRMode()) {
            RenewSubscriber renewSub = new RenewSubscriber(this);
            setRenewSubscriber(renewSub);
            renewSub.start();
        }
        return true;
    }

    public boolean start(String target) {
        return start(target, 3);
    }

    public boolean start() {
        return start("upnp:rootdevice", 3);
    }

    public boolean stop() {
        unsubscribe();
        SSDPNotifySocketList ssdpNotifySocketList = getSSDPNotifySocketList();
        ssdpNotifySocketList.stop();
        ssdpNotifySocketList.close();
        ssdpNotifySocketList.clear();
        SSDPSearchResponseSocketList ssdpSearchResponseSocketList = getSSDPSearchResponseSocketList();
        ssdpSearchResponseSocketList.stop();
        ssdpSearchResponseSocketList.close();
        ssdpSearchResponseSocketList.clear();
        HTTPServerList httpServerList = getHTTPServerList();
        httpServerList.stop();
        httpServerList.close();
        httpServerList.clear();
        Disposer disposer = getDeviceDisposer();
        if (disposer != null) {
            disposer.stop();
            setDeviceDisposer(null);
        }
        RenewSubscriber renewSub = getRenewSubscriber();
        if (renewSub != null) {
            renewSub.stop();
            setRenewSubscriber(null);
        }
        return true;
    }

    public void setUserData(Object data) {
        this.userData = data;
    }

    public Object getUserData() {
        return this.userData;
    }

    public void print() {
        DeviceList devList = getDeviceList();
        int devCnt = devList.size();
        Debug.message("Device Num = " + devCnt);
        for (int n = 0; n < devCnt; n++) {
            Device dev = devList.getDevice(n);
            Debug.message("[" + n + "] " + dev.getFriendlyName() + ", " + dev.getLeaseTime() + ", " + dev.getElapsedTime());
        }
    }
}
