import main
import mainform
import yandex_static_downloader as YSD
import sys
from PyQt5 import QtCore
from PyQt5 import QtWidgets
from PyQt5.QtWidgets import QWidget, QHBoxLayout, QLabel, QApplication, QListWidget, QMessageBox, QLineEdit
from PyQt5.QtCore import QModelIndex
from PyQt5.QtGui import QPixmap
def YSD_former_from_widget(center_longitude:QLineEdit, center_latitude:QLineEdit, 
    map_type:QListWidget, 
    spn_long:QLineEdit, spn_lat:QLineEdit, 
    sizeX:QLineEdit, sizeY:QLineEdit, 
    scale:QLineEdit):
    return YSD.yandex_static_downloader(center_longitude.text(), center_latitude.text(), 
    map_type.item(map_type.currentRow()).text(), 
    spn_long.text(), spn_lat.text(), 
    sizeX.text(), sizeY.text(), 
    scale.text() )