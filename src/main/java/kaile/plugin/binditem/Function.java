package kaile.plugin.binditem;
public class Function  {
    public static boolean IsBind(String Itemid){
        if(Itemid.contains("专属"))
        {
            return true;
        }
        return false;
    }
    public static boolean IsWillBind(String Itemid)
    {
        return Itemid.contains("待绑定");
    }
    public static String GetBindName(String Itemid){
        int start=Itemid.indexOf("【");
        int end=Itemid.indexOf("】");
        String Bind_name="";
        for(int i=start+1;i<end;i++){
            Bind_name=Bind_name+Itemid.toCharArray()[i];
        }
            return Bind_name;
    }
}
