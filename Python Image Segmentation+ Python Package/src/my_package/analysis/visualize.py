#Imports
from PIL import Image, ImageDraw
import numpy as np

def plot_visualization(image,gt_bboxes,names_list,mask_list): # Write the required arguments
  # The function should plot the predicted segmentation maps and the bounding boxes on the images and save them.
  # Tip: keep the dimensions of the output image less than 800 to avoid RAM crashes.
  #creates the image object from the numpy array
  image_temp = np.rollaxis(image, 0, 3)*255
  im = Image.fromarray(np.uint8((image_temp)))
  #check to see if user is printing only border boxes or a proper mask 
  if(len(gt_bboxes)==len(mask_list)):
    for x in range(min(3,len(gt_bboxes))):
      #converts each mask to img object and changes the pixels through iteration
      image_temp1 = np.rollaxis(mask_list[x], 0, 3)*255
      color = tuple(np.random.choice(range(256), size=3))
      try1=image_temp1.shape
      image_temp2=image_temp1.reshape(try1[0],try1[1])
      im1 = Image.fromarray(np.uint8((image_temp2)))
      im1=im1.convert('RGB')
      width = im1.size[0] 
      height = im1.size[1] 
      for i in range(0,width):# process all pixels
        for j in range(0,height):
          data = im1.getpixel((i,j))
          data1 = im.getpixel((i,j))
          #print(data) #(255, 255, 255)
          if (data[0]<=90 and data[1]<=90 and data[2]<=90):
              im1.putpixel((i,j),data1)
          else:
            im1.putpixel((i,j),color)
      #blends final image
      im = Image.blend(im, im1, 0.7)
    
  
  #draws only border boxes or creates proper mask image
  draw = ImageDraw.Draw(im)
  if(len(gt_bboxes)!=0 and len (names_list)==0 and len(mask_list)==0):
    for x in range(len(gt_bboxes)):
      draw.rectangle(gt_bboxes[x], outline ="red")
      
  else:
    for x in range(min(3,len(gt_bboxes))):
      
      draw.rectangle(gt_bboxes[x], outline ="red")
      temp_string='  '+names_list[x]
      draw.text((gt_bboxes[x][0]),temp_string, fill='white')
    

  return im