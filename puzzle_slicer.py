# This is a script to slice an image into a specified number of pieces
# and save them to a specified directory.

import os
import argparse
import numpy as np
from PIL import Image

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('-i', '--image', type=str, required=True, help='Path to image to slice')
    parser.add_argument('-o', '--output', type=str, help='Path to output directory')
    parser.add_argument('-c', '--columns', type=int, required=True, help='Number of columns to slice image into')
    parser.add_argument('-r', '--rows', type=int, required=True, help='Number of rows to slice image into')
    args = parser.parse_args()

    # If output directory is not provided, create one named after the image in the working directory
    if not args.output:
        image_name = os.path.splitext(os.path.basename(args.image))[0]
        args.output = os.path.join(os.getcwd(), image_name)

    # Create directory if it does not exist
    if not os.path.exists(args.output):
        os.makedirs(args.output)

    # Load image
    img = Image.open(args.image)
    img = np.array(img)

    # Get image dimensions
    height, width, channels = img.shape

    # Get slice dimensions
    slice_height = height // args.rows
    slice_width = width // args.columns

    # Slice image
    for y in range(args.rows):
        for x in range(args.columns):
            start_height = y * slice_height
            end_height = start_height + slice_height
            start_width = x * slice_width
            end_width = start_width + slice_width
            slice = img[start_height:end_height, start_width:end_width, :]
            slice = Image.fromarray(slice)
            slice.save(os.path.join(args.output, 'slice_{}_{}.png'.format(y, x)))

if __name__ == '__main__':
    main()