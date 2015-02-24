package impactservice;



import tools.Debug;
import tools.MyXMLReader;

public class Configuration {

  static long readConfigPolInterval = 0;;
  
  private static String impactWorkspace=null;//"/home/visadm/impactspace/";

  public static String getImpactWorkspace(){readConfig();return impactWorkspace+"/";}
  
  public static String getDownscalingPortalWorkspace(){return getImpactWorkspace()+"downscalingportal/";};
  
  public static String getHomePath(){
    return System.getProperty("user.home")+"/impactportal/";
  }
  
  public static String getHomeURLPrefix(){
    return "impactportal";
  }
  
  public static String getHomeURLHTTP(){
    return GlobalConfig.getServerHTTPURL()+getHomeURLPrefix();
  }
  
  public static String getHomeURLHTTPS(){
    return GlobalConfig.getServerHTTPSURL()+getHomeURLPrefix();
  }

  
  private static String getConfigFile(){
    return getHomePath()+"config.xml";
  }
  
  public static String getImpactServiceLocation(){
    return "/impactportal/ImpactService?";
  }
  
  
  public static void readConfig(){
    //Re-read the config file every 10 seconds.
    if(impactWorkspace != null && readConfigPolInterval != 0){
      if(System.currentTimeMillis()<readConfigPolInterval+10000)return;
    }
    readConfigPolInterval=System.currentTimeMillis(); 
    Debug.println("Reading configfile "+getConfigFile());
    MyXMLReader configReader = new MyXMLReader();
    try {
      configReader.parseXMLFile(getConfigFile());
    } catch (Exception e) {
      Debug.println("Unable to read "+getConfigFile());
      configReader = null;
      return;
    }

    impactWorkspace=configReader.getNodeValue("impactportal.impactworkspace");
    
    GlobalConfig.doConfig(configReader);
    DrupalConfig.doConfig(configReader);
    VercSearchConfig.doConfig(configReader);
    LoginConfig.doConfig(configReader);
    ExpertContact.doConfig(configReader);
    ADAGUCServerConfig.doConfig(configReader);
    PyWPSServerConfig.doConfig(configReader);
    DownloadScriptConfig.doConfig(configReader);
    
    configReader = null; 
  }
  
  
  public static String getEmailToSendFatalErrorMessages(){
    return "vreedede@knmi.nl";
  }
  
  public static class GlobalConfig{
    private static String serverURLHTTP="";
    private static String serverURLHTTPS="";
    
    public static String offlineMode;
    public static String defaultUserInOfflineMode;
    
    //public static String getServerHomeURL(){readConfig();return serverURLHTTP;}
    public static void doConfig(MyXMLReader configReader) {
      serverURLHTTP = configReader.getNodeValue("impactportal.serverurl");
      serverURLHTTPS = configReader.getNodeValue("impactportal.serverurlhttps");
      offlineMode = configReader.getNodeValue("impactportal.offlinemode");
      defaultUserInOfflineMode= configReader.getNodeValue("impactportal.defaultuseropenid");
    }
    public static String getServerHTTPURL(){
      readConfig();
      return serverURLHTTP;
    }
    public static String getServerHTTPSURL(){
      readConfig();
      return serverURLHTTPS;
    }
    public static boolean isInOfflineMode() {
      readConfig();
      if("true".equalsIgnoreCase(offlineMode))return true;
      return false;
    }
    public static String getDefaultUser() {
      //Only used in case of offline mode
      readConfig();
      return defaultUserInOfflineMode;
    }
  }
  
  public static class DrupalConfig{
    private static String drupalHost="<drupalhost>";
    private static String drupalBaseURL="<drupalbaseurl>";
    private static String drupalDirectory="<drupaldirectory>";
    private static String portalFilesLocation="http://climate4impact.eu/files/";
    public static void doConfig(MyXMLReader configReader){
      drupalHost=configReader.getNodeValue("impactportal.drupalconfig.drupalhost");
      drupalBaseURL=configReader.getNodeValue("impactportal.drupalconfig.drupalbaseurl");
      drupalDirectory=configReader.getNodeValue("impactportal.drupalconfig.drupaldirectory");
      //portalFilesLocation=configReader.getNodeValue("impactportal.drupalconfig.portalfileslocation");
    }
    public static String getDrupalHost(){readConfig();return drupalHost;}
    public static String getDrupalBaseURL(){readConfig();return drupalBaseURL;}
    public static String getDrupalDirectory(){readConfig();return drupalDirectory;}
    public static String getPortalFilesLocation() {
      return portalFilesLocation;
      
    }
  }
  
  public static class VercSearchConfig{
    private static String __deprecated_vercSearchURL="<deprecated>";
    private static String esgfSearchURL="<esgfSearchURL>";
    public static void doConfig(MyXMLReader configReader){
      __deprecated_vercSearchURL=configReader.getNodeValue("impactportal.searchconfig.vercsearchurl");
      esgfSearchURL=configReader.getNodeValue("impactportal.searchconfig.esgfsearchurl");
    }
    public static String __getDeprecatedVercSearchURL(){readConfig();return __deprecated_vercSearchURL;}
    public static String getEsgfSearchURL(){readConfig();return esgfSearchURL;}
  }
  
  public static class LoginConfig{
    
    private static String myProxyServerHost = "<proxyhost>";
    private static int myProxyServerPort = 7512;
    private static String trustStoreFile = null;
    private static String trustStorePassword= null;
    private static String myProxyDefaultPassword = "<defaultpassword>";
    private static String trustRootsLocation = null;
    
    // myProxyDefaultUserName should be null, because in that case the openid identifier from the current user is used.
    // It can be set to override a custom username, e.g. in case of testing on a workstation.
    private static String myProxyDefaultUserName = null;
    public static void doConfig(MyXMLReader configReader){
      myProxyServerHost=configReader.getNodeValue("impactportal.loginconfig.myproxyserverhost");
      myProxyServerPort=Integer.parseInt(configReader.getNodeValue("impactportal.loginconfig.myproxyserverport"));
      trustStoreFile=configReader.getNodeValue("impactportal.loginconfig.truststorefile");
      trustStorePassword=configReader.getNodeValue("impactportal.loginconfig.truststorepassword");
      myProxyDefaultUserName = configReader.getNodeValue("impactportal.loginconfig.myproxyserverusernameoverride");
      myProxyDefaultPassword = configReader.getNodeValue("impactportal.loginconfig.myproxyserverpassword");
      trustRootsLocation = configReader.getNodeValue("impactportal.loginconfig.trustrootslocation");
    }
    
    public static String getMyProxyServerHost(){readConfig();return myProxyServerHost;}
    public static int getMyProxyServerPort(){readConfig();return myProxyServerPort;}
    public static String getTrustStoreFile(){readConfig();return trustStoreFile;}
    public static String getTrustStorePassword(){readConfig();return trustStorePassword;}
    public static String getMyProxyDefaultPassword(){readConfig();return myProxyDefaultPassword;}
    public static String getMyProxyDefaultUserName(){readConfig();return myProxyDefaultUserName;}

    public static String getTrustRootsLocation() {
      return trustRootsLocation;
    }

    
  }
  

  public static class ExpertContact{
    static String[] addresses={};
    
    public static void doConfig(MyXMLReader configReader){
      addresses = configReader.getNodeValue("impactportal.expertcontact.mailaddresses").split(",");
    }
    
    public static String[] getEmailAddresses(){
       readConfig();return  addresses;
    }
  }

  public static class ADAGUCServerConfig{
    
    private static String ADAGUCExecutable="";
    
    private static String[] environmentVariables = {
        };
    
    public static void doConfig(MyXMLReader configReader){
      ADAGUCExecutable=configReader.getNodeValue("impactportal.adagucserverconfig.adagucexecutable");
      environmentVariables = configReader.getNodeValues("impactportal.adagucserverconfig.exportenvironment");
    }
    
    public static String[] getADAGUCExecutable() {
      readConfig();
      String commands[]={ADAGUCExecutable};
      return commands;
    }
    
    public static String[] getADAGUCEnvironment() {
      readConfig();
      return environmentVariables;
    }
  }
  
  public static class PyWPSServerConfig{
    
    private static String PyWPSExecutable="";
    
    private static String[] environmentVariables = { };
    
    public static void doConfig(MyXMLReader configReader){
      PyWPSExecutable=configReader.getNodeValue("impactportal.pywpsconfig.pywpsexecutable");
      
      environmentVariables = configReader.getNodeValues("impactportal.pywpsconfig.exportenvironment");
    }
    
    public static String[] getPyWPSExecutable() {
      readConfig();
      String commands[]={PyWPSExecutable};
      return commands;
    }
    
    public static String[] getPyWPSEnvironment() {
      readConfig();
      return environmentVariables;
    }
  }
  public static class DownloadScriptConfig{
    private static String downloadScriptTemplate="";
    
    public static void doConfig(MyXMLReader configReader){
      downloadScriptTemplate=configReader.getNodeValue("impactportal.downloadscriptconfig.downloadscripttemplate");
    }
    
    public static String getDownloadScriptTemplate() {
      readConfig();
      return downloadScriptTemplate;
    }
  }
}
