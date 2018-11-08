import urllib
import urllib.request

class yandex_static_downloader:        
    def __init__(self, center_longitude, center_latitude, map_type:str, spn_long = "0.01", spn_lat = "0.01", sizeX = "650", sizeY = "450", scale = "1.0"):
        self.center_longitude = center_longitude
        self.center_latitude = center_latitude
        self.map_type = map_type
        self.spn_long = spn_long
        self.spn_lat = spn_lat
        self.sizeX = sizeX
        self.sizeY = sizeY
        self.scale = scale
    
    @staticmethod
    def form_request(self):
        separator = "&"
        retVal =  'https://static-maps.yandex.ru/1.x/?'+"l="+self.map_type+separator+"ll="+self.center_longitude+","+self.center_latitude+separator+"spn="+self.spn_long+","+self.spn_lat+separator+"size="+self.sizeX+","+self.sizeY+separator+"scale="+self.scale
        return retVal
    
    def download_from_link(imgurl:str, name_to_save):
        print("imgurl=".format(imgurl))
        urllib.request.urlretrieve(imgurl, name_to_save)
        print("Image was downloaded from {0} and saved as{1}".format(imgurl, name_to_save)) 
    @staticmethod
    def download(self,  name_to_save:str = "yandex_static_map.png"):
        yandex_static_downloader.download_from_link(yandex_static_downloader.form_request(self), name_to_save)
        
