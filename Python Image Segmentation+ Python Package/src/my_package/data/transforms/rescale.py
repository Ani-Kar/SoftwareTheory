#Imports
from PIL import Image
import numpy as np

class RescaleImage(object):
    '''
        Rescales the image to a given size.
    '''

    def __init__(self, output_size):
        '''
            Arguments:
            output_size (tuple or int): Desired output size. If tuple, output is
            matched to output_size. If int, smaller of image edges is matched
            to output_size keeping aspect ratio the same.
        '''
        self.hold_new_size=output_size

    def __call__(self, image):
        '''
            Arguments:
            image (numpy array or PIL image)

            Returns:
            image (numpy array or PIL image)

            Note: You do not need to resize the bounding boxes. ONLY RESIZE THE IMAGE.
        '''
        arr = np.array([0])
        if(type(image)==type(arr)):
            image = Image.fromarray(image)
        new_tuple=(50,50)
        #checks if input is tuple or int and adjusts accordingly
        if(type(self.hold_new_size)==int):
            tuple1=image.size
            new_size=int((max(tuple1)/min(tuple1))*self.hold_new_size)
            index=tuple1.index(max(tuple1))
            temp_list=list(new_tuple)
            temp_list[index]=new_size
            temp_list[(index+1)%2]=self.hold_new_size
            new_tuple=tuple(temp_list)
        else:
            new_tuple=self.hold_new_size

        return image.resize(new_tuple)

        # Write your code here



