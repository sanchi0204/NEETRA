//package com.example.safetapp;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.net.NetworkInfo;
//import android.net.wifi.p2p.WifiP2pManager;
//import android.widget.Toast;
//
//public class WifiBroadCastReceiver extends BroadcastReceiver {
//    private WifiP2pManager manager;
//    private WifiP2pManager.Channel channel;
//    private CheckWifi mainActivity;
//
//    public WifiBroadCastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, CheckWifi mainActivity) {
//        this.manager = manager;
//        this.channel = channel;
//        this.mainActivity = mainActivity;
//    }
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//
//        String action = intent.getAction();
//        if(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action))
//        {
//            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
//            if(state == WifiP2pManager.WIFI_P2P_STATE_ENABLED)
//            {
//                Toast.makeText(mainActivity, "WIfI is ON", Toast.LENGTH_SHORT).show();
//            }
//            else
//            {
//                Toast.makeText(mainActivity, "WIfI is OFF", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        else if(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action))
//        {
//            if(manager!=null)
//            {
//                manager.requestPeers(channel, mainActivity.peerListListener);
//            }
//        }
//        else if(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action))
//        {
//           if(manager==null)
//           {
//               return;
//           }
//
//            NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
//           if(networkInfo.isConnected())
//           {
//               manager.requestConnectionInfo(channel, mainActivity.connectionInfoListener);
//           }
//           else
//           {
//               mainActivity.status.setText("Device Disconnected");
//           }
//        }
//        else if(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action))
//        {
//            //do something
//        }
//    }
//}
