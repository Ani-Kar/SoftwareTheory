from turtle import shape
from my_package.data.transforms.rotate import RotateImage
from my_package.data.transforms.rescale import RescaleImage
from my_package.data.transforms.blur import BlurImage
from my_package.data.transforms.flip import FlipImage
from my_package.data.transforms.crop import CropImage
from my_package.data.dataset import Dataset
from my_package.analysis.visualize import plot_visualization
from my_package.model import InstanceSegmentationModel
from matplotlib import cm, transforms
import matplotlib.pyplot as plt
import numpy as np
from os import path
import warnings
from numpy import empty_like
#removes standard warnings
warnings.filterwarnings("ignore")

####### ADD THE ADDITIONAL IMPORTS FOR THIS ASSIGNMENT HERE #######
from functools import partial
from tkinter import *
from tkinter import ttk
from tkinter import filedialog
from PIL import Image, ImageTk

# Define the function you want to call when the filebrowser button is clicked.
def fileClick(clicked, dataset, segmentor):
    # global variables for use in other functions without passing them 
    global file_name,dir_path1,dir_path2
    # default file name
    file_name=""
    # takes input from dialog box with default filetypes of images
    file_name = filedialog.askopenfilename(filetypes=[('JPG Files', '*.jpg'), ('PNG Files', '*.png'), ('JPEG Files', '*.jpeg')])
    print(file_name)
    # check to see if file name isnt default
    if file_name!="":
        # adjusts the image and calls segmentor and plot visualization to get the appropriate images and saves them
        img=Image.open(file_name)
        np_img=np.array(img)/255
        np_img=np.rollaxis(np_img, 2, 0)
        seg_store = []
        null_store=[]
        seg_store=(segmentor.__call__(np_img))
        image_temp1=plot_visualization(np_img,seg_store[0],seg_store[2],null_store)
        for x in range(min(3,len(seg_store[0]))):
            seg_store[0][x]=[(0,0),(0,0)]
            seg_store[2][x]=""
        image_temp2=plot_visualization(np_img,seg_store[0],seg_store[2],seg_store[1])
        file_path = path.abspath(__file__) # full path of your script
        dir_path = path.dirname(file_path) # full path of the directory of your script
        dir_path1=dir_path+'\\GUI output\\boxes.png'
        dir_path2=dir_path+'\\GUI output\\masks.png'
        image_temp1.save(dir_path1,'PNG')
        image_temp2.save(dir_path2,'PNG')
        e.delete(0, 'end')
        e.insert(0, f"""{file_name}""")
    # calls process afterwards to immediately place correct image
    process(clicked)

def process(clicked):

    global original
    global duplicate
    # check to see if file is actually given or not
    if file_name=="":
        e.delete(0, 'end')
        e.insert(0, f"No Image Selected!")
        return
    # adds image to root tkinter and sets appropriate size according to the dropdown menu selected item
    image = Image.open(file_name)
    original = ImageTk.PhotoImage(image)
    original_label = Label(root, image=original,bg='#d4a6f2')
    if clicked.get() == "Segmentation":
        img_path = dir_path2
        final_img = Image.open(img_path)
        duplicate = ImageTk.PhotoImage(final_img)
        duplicate_label = Label(root, image=duplicate, bg='#d4a6f2')
        original_label.grid(row=2,column=0, columnspan=2)
        duplicate_label.grid(row=2,column=2, columnspan=20)
    else:
        img_path = dir_path1
        final_img = Image.open(img_path)
        duplicate = ImageTk.PhotoImage(final_img)
        duplicate_label = Label(root, image=duplicate, bg='#d4a6f2')
        original_label.grid(row=2,column=0, columnspan=2)
        duplicate_label.grid(row=2,column=2, columnspan=20)

if __name__ == '__main__':
    # creates root
    root = Tk()
    root.title("PYTHON IMAGE PROCESSING")
    root.config(bg='#d4a6f2')
    root.minsize(0, 0)

	# Setting up the segmentor model.
    annotation_file = './data/annotations.jsonl'
    transforms = []
    # Instantiate the segmentor model.
    segmentor = InstanceSegmentationModel()
    # Instantiate the dataset.
    dataset = Dataset(annotation_file, transforms=transforms)
    file_name=""
    # creates button and combobox and adds to grid
    options = ["Segmentation", "Bounding-box"]
    clicked = StringVar()
    clicked.set(options[0])
    global e
    e = Entry(root, width=70)
    e.grid(row=0, column=0,padx=10)
    img_path = {}
    img_path["path"] = None
    selectFile = Button(text='file_path', bg="#9729E0",command=partial(fileClick, clicked, dataset, segmentor), padx=2, pady=2)

    DropDown = ttk.Combobox(root, width=25, values=options, textvariable=clicked,state="readonly")

    ProcessImg = Button(root, text="Process", bg="#9729E0",command=partial(process, clicked), padx=2, pady=2)
    selectFile.grid(row=0, column=1,pady=5)
    DropDown.grid(row=0, column=2,padx=10,pady=5)
    ProcessImg.grid(row=0, column=3,pady=5)
    # loops main
    root.mainloop()
