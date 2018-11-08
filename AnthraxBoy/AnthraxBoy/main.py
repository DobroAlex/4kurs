import sys
import mainform
import downloads_handler 
import yandex_static_downloader as YSD
import YSD_former_from_widget as YSD_former 
from PyQt5 import QtCore
from PyQt5 import QtWidgets
from PyQt5.QtWidgets import QWidget, QHBoxLayout, QLabel, QApplication, QListWidget, QMessageBox, QPushButton
from PyQt5.QtCore import QModelIndex, QObject, pyqtSignal
from PyQt5.QtGui import QPixmap
class main_window( QtWidgets.QMainWindow, mainform.Ui_MainWindow):
    def __init__(self):
        super().__init__()
        self.setupUi(self)
    
def init_map_modes_list(list_widget:QListWidget, list_of_modes:list):
        list_widget.addItems(list_of_modes)
        list_widget.setCurrentRow(1)
def redraw_map_on_change(map_request:YSD.yandex_static_downloader):
    map_request.download(map_request)

def main():
    app = QtWidgets.QApplication(sys.argv)
    window = main_window()
    map_type_list = window.map_type_list_widget
    init_map_modes_list(map_type_list, ["map,skl,trf","map","skl","map,skl","trf"])
    window.show()
    #msg = QMessageBox()
    #msg.setText("currentRow = {0}".format(window.map_type_list_widget.currentRow()))
    #msg.show()
    downloads_handler.clear_previous_map("yandex_static_map.png")
    map_request = YSD_former.YSD_former_from_widget(window.map_center_longitude_line_edit, window.map_center_latitude_line_edit,
    window.map_type_list_widget,
    window.map_spn_latitude_line_edit, window.map_spn_longitude_line_edit,
    window.map_size_X_line_edit, window.map_size_Y_line_edit,
    window.map_objects_scale_line_edit)
    #map_request = YSD.yandex_static_downloader(window.map_center_longitude_line_edit.text(), window.map_center_latitude_line_edit.text(), map_type_list.item(map_type_list.currentRow()).text())
    #map_request = YSD.yandex_static_downloader("34.097528", "44.949997", "map,trf")
    map_request.download(map_request)
    window.map_label.setPixmap(QPixmap("yandex_static_map.png"))
    window.update_map_push_button.clicked.connect(lambda: redraw_map_on_change(map_request))
    
    app.exec_()
if __name__ == "__main__":
    main()