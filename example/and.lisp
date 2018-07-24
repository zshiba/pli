(define and (lambda (p1 p2) (cond (p1 (cond (p2 t)
                                            (t nil)))
                                  (t nil))))

(and t t)

(and t nil)

(and () t)

(and ((lambda (x) (eq x (quote a))) (quote a))
     ((lambda (x) (eq x (quote b))) (quote b)))

(define pred1 (lambda (x) (atom x)))

(define pred2 (lambda (x) (cond ((atom x) nil)
                                (t t))))

(and (pred1 (quote a)) (pred2 (quote (b c))))

(and (pred1 (quote a)) (pred2 (quote b)))
