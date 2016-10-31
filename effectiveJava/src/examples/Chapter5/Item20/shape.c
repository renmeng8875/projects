/* Discriminated union */
#include "math.h"
typedef enum {RECTANGLE, CIRCLE} shapeType_t;

typedef struct {
    double length;
    double width;
} rectangleDimensions_t;

typedef struct {
    double radius;
} circleDimensions_t;

typedef struct {
    shapeType_t tag;
    union {
        rectangleDimensions_t rectangle;
        circleDimensions_t    circle;
    } dimensions;
} shape_t;

double area(shape_t *shape) {
    switch(shape->tag) {
      case RECTANGLE: {
        double length = shape->dimensions.rectangle.length;
        double width  = shape->dimensions.rectangle.width;
        return length * width;
      }
      case CIRCLE: {
        double r = shape->dimensions.circle.radius;
        return M_PI * (r*r);
      }
      default: return -1.0; /* Invalid tag */
    }
}

int main() {
    shape_t rectangle, circle;
    rectangle.tag = RECTANGLE;
    rectangle.dimensions.rectangle.length = 2.0;
    rectangle.dimensions.rectangle.width = 3.0;
    printf("Rectangle area: %f\n", area(&rectangle));

    circle.tag = CIRCLE;
    circle.dimensions.circle.radius = 1.0;
    printf("Circle area: %f\n", area(&circle));

    return 0;
}
